package fordream.http;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.handler.StaticResourcesHandler;

/**
 * A simple to use <code>DreamHttpd</code>
 */
public class SimplyDreamHttpd extends DreamHttpd {
    public SimplyDreamHttpd(int port, String webRoot) {
        super(port, webRoot);
        this.registerHandler(new StaticResourcesHandler()); // register a static resource handler
    }

    public static void main(String[] args) throws Exception {
        int port = -1;
        String root = null;
        // set http server port
        if (args.length > 0)
            port = Integer.valueOf(args[0]);
        else
            port = 2046;

        // set http server root url
        if (args.length > 1)
            root = args[1];
        else
            root = System.getProperty("user.dir");

        DreamHttpd httpd = new SimplyDreamHttpd(port, root); // init
        httpd.start(); // start the server

        System.out.println(String.format("Start Http Server with %d and %s", port, root));

        System.out.println("Press Ctrl + C to exit.");

        while (true) System.in.read();
    }
}
