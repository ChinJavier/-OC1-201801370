/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pr1compi;

/**
 *
 * @author Javier C
 */
public class Token {

    private TipoToken tipo; //El tipo del token
    private String lexema;  //Almaceno el lexema
    private int correlativo; //Correlativo del Token


    public enum TipoToken {
        Comentario_una_linea,
        Inicio_comentario_multi,
        Fin_comentario_multi,
        Llave_izq,
        Llave_der,
        Asignacion_flecha,
        Punto_coma,
        Dos_puntos,
        Porcentaje_doble,
        Numero,
        Id,
        Cadena,
        Coma,
        Separador_conjuntos,
        Punto,
        Barra,
        Interrogante,
        Cerradura,
        Simbolo_Mas,
        Reservada_conj,
        LexemaEntrada
    }
    
    public Token(TipoToken tipoToken, String valor, int correlativo){
        this.tipo = tipoToken;
        this.lexema = valor;
        this.correlativo = correlativo;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }
    
    
    
}
