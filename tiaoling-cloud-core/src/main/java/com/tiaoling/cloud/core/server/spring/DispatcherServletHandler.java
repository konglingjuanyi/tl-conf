package com.tiaoling.cloud.core.server.spring;

import com.tiaoling.cloud.core.utils.PropertyConfigurer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import io.netty.handler.stream.ChunkedStream;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yhl on 2016/9/29.
 */
public class DispatcherServletHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private Logger logger = LoggerFactory.getLogger(DispatcherServletHandler.class);
    private static final String URI_ENCODING = PropertyConfigurer.getContextProperty("key_uri_encoding");
    private final Map<String,DispatcherServlet> servletMap;
    private final ServletContext servletContext;
    private Map<String,String> servletMappings;
    private boolean isDispatch;
    public static String LOGTHREAD_ID ="logthreadId";

    protected DispatcherServletHandler(Map<String,DispatcherServlet> servletMap, MockServletContext servletContext,
                                       Map<String,String> mp) {
        this.servletMap=servletMap;
        this.servletContext=servletContext;
        this.servletMappings=mp;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        boolean flag = HttpMethod.POST.equals(request.getMethod()) || HttpMethod.GET.equals(request.getMethod());
        MDC.put(LOGTHREAD_ID, UUID.randomUUID().toString().replace("-",""));
        try {
            if(!request.getDecoderResult().isSuccess() || !flag)
            {
                //netty解析http请求失败
                //sendError(ctx,HttpResponseStatus.BAD_REQUEST,
                //"NS服务器解析不了本次请求，这不是一次标准的HTTP GET/POST请求");
                sendError(ctx, HttpResponseStatus.valueOf(500), PropertyConfigurer.getContextProperty("system.internal_server_error"));
                return;
            }
            //servlet请求和响应，这里使用spring-test包中的servlet api实现
            //1、将netty的http请求转换为servletHttp请求
            MockHttpServletRequest servletRequest = createServletRequest(request);
            //2、创建一个空的Servlethttp响应
            MockHttpServletResponse servletResponse = new MockHttpServletResponse();
            //3、调用Servlet处理
            String temp = servletRequest.getRequestURI();
            for(String item : servletMappings.keySet())
            {
                String tpItem = item;
                item=item.replace(".","\\.").replace("*",".+");
                Pattern pat = Pattern.compile(item);
                Matcher mat = pat.matcher(temp);
                if(mat.find())
                {
                    DispatcherServlet dispatcherServlet = this.servletMap.get(this.servletMappings.get(tpItem));
                    dispatcherServlet.service(servletRequest,servletResponse);
                    isDispatch=true;
                    break;
                }
            }
            if(!isDispatch)
            {
                //返回异常信息
                sendError(ctx,HttpResponseStatus.valueOf(404), PropertyConfigurer.getContextProperty("system.not_found"));
                return;
            }
            isDispatch=false;
            //4、将ServletHttp响应转换成Netty的Http响应并处理
            sendResponse(ctx,servletResponse);
        } catch (Exception e) {
            logger.error(e.toString());
            //e.printStackTrace();
        } finally {
            MDC.remove(LOGTHREAD_ID);
        }
    }


    private MockHttpServletRequest createServletRequest(FullHttpRequest msg) throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest(this.servletContext);
        String uri = msg.getUri();
        uri= new String(uri.getBytes("ISO8859-1"),URI_ENCODING);
        uri= URLDecoder.decode(uri,URI_ENCODING);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        //cookie
        String cookieStr = msg.headers().get("Cookie");
        if(cookieStr!=null && !"".equals(cookieStr.trim()))
        {
            cookieStr=cookieStr.trim();
            Set<Cookie> nettyCookies = CookieDecoder.decode(msg.headers().get("Cookie"));
            List<javax.servlet.http.Cookie> servletCookies = new ArrayList<javax.servlet.http.Cookie>();
            for(Cookie nCookie:nettyCookies)
            {
                try {
                    javax.servlet.http.Cookie sCookie = new javax.servlet.http.Cookie(nCookie.name(),nCookie.value());
                    sCookie.setComment(nCookie.comment());
                    if(nCookie.domain()!=null) sCookie.setDomain(nCookie.domain());
                    sCookie.setMaxAge((int) nCookie.maxAge());
                    sCookie.setPath(nCookie.path());
                    sCookie.setVersion(nCookie.version());
                    servletCookies.add(sCookie);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            javax.servlet.http.Cookie[] cookies =  new javax.servlet.http.Cookie[servletCookies.size()];
            request.setCookies(servletCookies.toArray(cookies));
        }
        //headers
        for (CharSequence chName : msg.headers().names()) {
            String name = chName.toString();
            for (String value : msg.headers().getAll(name))
            {
                request.addHeader(name,value);
            }
        }
        //request method
        request.setMethod(msg.getMethod().name().toString());
        //request uri
        String path = uriComponents.getPath();
        path=URLDecoder.decode(path,URI_ENCODING);
        request.setRequestURI(path);
        request.setPathInfo(path);
        String contextRoot = PropertyConfigurer.getContextProperty("context-root");
        if(path.startsWith(contextRoot))
        {
            request.setServletPath(contextRoot);
        }
        else {
            throw new Exception("请输入正确的上下文环境");
        }
        if(uriComponents.getScheme()!=null)
        {
            request.setScheme(uriComponents.getScheme());
        }
        if(uriComponents.getHost()!=null) request.setServerName(uriComponents.getHost());
        if(uriComponents.getPort()!=-1) request.setServerPort(uriComponents.getPort());
        //request content body
        ByteBuf content = msg.content();
        content.readerIndex(0);
        byte[] datas = new byte[content.readableBytes()];
        content.readBytes(datas);
        request.setContent(datas);
        //request parameters
        try {
            if(uriComponents.getQuery()!=null)
            {
                String query = UriUtils.decode(uriComponents.getQuery(),URI_ENCODING);
                request.setQueryString(query);
            }
            for(Map.Entry<String,List<String>> entry : uriComponents.getQueryParams().entrySet())
            {
                for(String value : entry.getValue())
                {
                    request.addParameter(UriUtils.decode(entry.getKey(),URI_ENCODING),UriUtils.decode(value==null?"":value,URI_ENCODING));
                }
            }
        } catch (UnsupportedEncodingException e) {
            //不应该发生这种情况，如果出现请坚持URI_ENCODING是否正确
            e.printStackTrace();
        }
        if(HttpMethod.POST.equals(msg.getMethod()))
        {
            Charset charset = Charset.forName(URI_ENCODING);
            HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE),
                    msg,charset);
            if(postRequestDecoder.isMultipart())
            {
                List<InterfaceHttpData> dataList = postRequestDecoder.getBodyHttpDatas();
                for (InterfaceHttpData data : dataList)
                {
                    String name = data.getName();
                    String value = null;
                    if(InterfaceHttpData.HttpDataType.Attribute==data.getHttpDataType())
                    {
                        MixedAttribute attribute = (MixedAttribute) data;
                        attribute.setCharset(charset);
                        value=attribute.getValue();
                        request.addParameter(UriUtils.decode(name,URI_ENCODING),UriUtils.decode(value==null?"":value,URI_ENCODING));
                    }else if(InterfaceHttpData.HttpDataType.FileUpload==data.getHttpDataType())
                    {
                        //文件域，不处理，已复制request content
                    }else if(InterfaceHttpData.HttpDataType.InternalAttribute==data.getHttpDataType())
                    {
                        //网上的例子也没有处理，API也没有说明
                    }
                }
            }else
            {
                String postContent = new String(datas,Charset.forName(URI_ENCODING));
                logger.debug(postContent);
                String[] params = postContent.split("&");
                for(String param : params)
                {
                    String[] _kv = param.split("=");
                    String[] kv = new String[2];
                    kv[0]=_kv[0];
                    if(_kv.length>=2)
                    {
                        kv[1]=URLDecoder.decode(_kv[1],URI_ENCODING);
                    }
                    else
                    {
                        kv[1]="";
                    }
                    request.addParameter(kv[0],kv[1]);
                }
            }
        }
        return request;
    }
    private void sendResponse(ChannelHandlerContext ctx,MockHttpServletResponse servletResponse)
    {
        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        if(status.equals(HttpResponseStatus.NOT_FOUND) || status.equals(HttpResponseStatus.INTERNAL_SERVER_ERROR)
                || status.equals(HttpResponseStatus.FORBIDDEN))
        {
            if(ctx.channel().isActive())
            {
                sendError(ctx,status, PropertyConfigurer.getContextProperty("system.not_found"));
                return;
            }
        }
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,status);
        for(Object name : servletResponse.getHeaderNames())
        {
            for(Object value : servletResponse.getHeaders(name.toString()))
            {
                response.headers().add(name.toString(),value);
            }
        }
        String domain = PropertyConfigurer.getContextProperty("key_cookie_domain");
        for(javax.servlet.http.Cookie sCookie : servletResponse.getCookies()) {
            StringBuilder _cookie = new StringBuilder();
            _cookie.append(sCookie.getName()).append("=").append(sCookie.getValue());
            if (sCookie.getMaxAge() != 0) {
                long _now = System.currentTimeMillis();
                long _exp = _now + sCookie.getMaxAge() * 1000;
                Date expDate = new Date(_exp);
                String date = getGMTtime(expDate);
                _cookie.append(";expires=").append(date);
            }
            if (StringUtils.isNotEmpty(sCookie.getDomain())) domain = sCookie.getDomain();
            String path = "/";
            if (StringUtils.isNotEmpty(sCookie.getPath())) path = sCookie.getPath();
            _cookie.append(";path=").append(path);
            _cookie.append(";domain=").append(domain);
            response.headers().set("Set-Cookie",_cookie.toString());
        }
        response.headers().set("Cache-Control","private");
        response.headers().set("Date",getGMTtime(new Date()));
        response.headers().set("Server","NS/1.0");
        //write the initial line and headers
        ctx.write(response);
        InputStream contentStream = new ByteArrayInputStream(servletResponse.getContentAsByteArray());
        //write the content
        ChannelFuture writeFuture = ctx.writeAndFlush(new ChunkedStream(contentStream));
        writeFuture.addListener(ChannelFutureListener.CLOSE);
    }
    private String getGMTtime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    @SuppressWarnings("unused")
    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status, String msg) {
        StringBuilder error = new StringBuilder();
        error.append("{").append("status:").append(status.code())
                .append(",msg:").append(msg == null ? "" : msg).append("}");
        HttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(
                error.toString(), CharsetUtil.UTF_8));
        response.headers().set("ContentType",
                "text/plain; charset=UTF-8");
        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
