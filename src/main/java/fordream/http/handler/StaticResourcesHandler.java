package fordream.http.handler;

import fi.iki.elonen.NanoHTTPD;
import fordream.http.AbstractRequestHandler;
import fordream.http.MimeHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.Response.Status;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Just return the static resource. If there is no file can be found return the 404 HTTP Code(NotFound)
 * This handler should be resisted in the end. it processes all request any way.
 */
public class StaticResourcesHandler extends AbstractRequestHandler {

    @Override
    public boolean doHandler(Map<String, String> args, String uri) {
        return true;
    }

    @Override
    protected NanoHTTPD.Response onGet(String root, Map<String, String> args, NanoHTTPD.IHTTPSession session) {
        String path;
        if ("/".equals(session.getUri())) {
            path = root + File.separator + "index.html"; // defualt request file
        } else
            path = root + session.getUri();
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                return newFixedLengthResponse(Status.FORBIDDEN, MIME_PLAINTEXT, null);
            }
            try {
                FileInputStream is = new FileInputStream(file);
                // nano httpd will close the stream
                return newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                        MimeHelper.instance().getMime(file.getAbsolutePath()), is, file.length());
            } catch (FileNotFoundException e) { // checked
                e.printStackTrace();
            }
        } else {
            // http code 404
            return newFixedLengthResponse(Status.NOT_FOUND, MIME_PLAINTEXT, null);
        }

        return null;
    }
}
