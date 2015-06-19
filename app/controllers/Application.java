package controllers;

import play.Logger;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import synchup.SynchTriggerer;
import views.html.index;

/**
 * The type Application.
 */
public class Application extends Controller {

    /**
     * Index result.
     *
     * @return the result
     */
    public static Result index() {
        // if they are authenticated:
        return ok(index.render());
        // if they are not authenticated:
        // return ok(signup.render());
    }

    /**
     * Ws index.
     *
     * @return the web socket
     */
    public static WebSocket<String> wsIndex() {
        return new WebSocket<String>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

                // For each event received on the socket,
                in.onMessage(new F.Callback<String>() {
                    public void invoke(String event) {

                        // Log events to the console
                        System.out.println(event);

                    }
                });

                // When the socket is closed.
                in.onClose(new F.Callback0() {
                    public void invoke() {

                        System.out.println("Disconnected");

                    }
                });

                // Send a single 'Hello!' message
                out.write("Hello: " + in + "!");

            }

        };
    }


    /**
     * Sock handler.
     *
     * @return the web socket
     */
    public static WebSocket<String> sockHandler() {
        return new WebSocket<String>() {
            // called when the websocket is established
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
                // register a callback for processing instream events
                in.onMessage(new F.Callback<String>() {
                    public void invoke(String event) {
                        Logger.info(event);
                    }
                });

                // write out a greeting
                out.write("I'm contacting you regarding your recent websocket.");
            }
        };
    }

    static {
        try {
            SynchTriggerer.calllMeToTrigger();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

}
