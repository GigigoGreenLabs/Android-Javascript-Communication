package es.gigigolabs.interfaces;

/**
 * Created by josemoralejo on 16/12/15.
 */
public interface AndroidNativeInterface {

    /**
     * Mensaje que va de web(javascript) a nativo (android)
     * @param msg
     */
    public void w2a_showMessage( String msg);

    /**
     * Mensaje que va de nativo (android) a web(javascript)
     * @param msg
     */
    public void a2w_showMessage( String msg);
}
