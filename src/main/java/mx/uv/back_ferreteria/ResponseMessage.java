package mx.uv.back_ferreteria;

public class ResponseMessage {
    private String mensaje;

    public ResponseMessage(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
