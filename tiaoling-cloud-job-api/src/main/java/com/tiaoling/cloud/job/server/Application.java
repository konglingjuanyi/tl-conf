package com.tiaoling.cloud.job.server;

import com.tiaoling.cloud.core.server.Server;
import com.tiaoling.cloud.core.server.spring.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by yhl on 2016/10/11.
 */
public class Application {
    private static Logger log = LoggerFactory.getLogger(Application.class);

    private static Server server;
    private static void listenCtrl(final int cport) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    log.info("server listen to control port " + cport);
                    ServerSocket ss = new ServerSocket(cport);
                    while (true) {
                        Socket s = null;
                        s = ss.accept();
                        if (s == null)
                            continue;
                        Scanner sc = new Scanner(s.getInputStream());
                        String line = sc.nextLine();
                        if (line.equals("stop")) {
                            log.info("server is stoping...");
                            shutdown(0);
                        }
                    }
                } catch (Exception e) {
                    log.error("listen to control port fail", e);
                    shutdown(-1);
                }
            }
        }, "ctrl-thread");
        t.start();
    }

    public static void main(String[] args) throws IOException {
        if (args != null && args.length != 0 && (args[0].equals("stop"))) {
            int ctrlPort = Integer.parseInt(args[1]);
            Socket s = new Socket("localhost", ctrlPort);
            PrintWriter w = new PrintWriter(s.getOutputStream());
            w.println("stop");
            w.flush();
            w.close();
            s.close();
        } else if (args == null || args.length == 0 || args[0].equals("start")) {
            int serverPort = Integer.parseInt(args[1]);
            server = new Server(serverPort);
            try {
                listenCtrl(Integer.parseInt(args[2]));
                server.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                shutdown(-1);
            }
        }
    }

    private static void shutdown(int code) {
        Iterator<String> iterator = server.getDispatcherServletChannelInitializer()
                .getDispatcherServlet().keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            server.getDispatcherServletChannelInitializer().getDispatcherServlet().get(key)
                    .destroy();
        }
        ApplicationContext ac = ApplicationContextHolder.getRootContext();
        ac.publishEvent(new ContextClosedEvent(ac));
        ac.publishEvent(new ContextStoppedEvent(ac));
        System.exit(code);
    }
}
