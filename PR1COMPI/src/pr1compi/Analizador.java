package pr1compi;

import java.util.LinkedList;

/**
 *
 * @author Javier C
 */
public class Analizador {

    private int estado;  // Para manejar el estado en el que me encuentro
    private String auxLexico; //Es mi recolector para lexemas
    private int subCadena;

    private LinkedList<Token> listaTokens = new LinkedList<Token>(); //Guarda mi lista de Tokens

    public Analizador() {

    }

    public LinkedList<Token> Analizar(String cadena) {
        estado = 0;
        auxLexico = "";
        char c;

        for (int i = 0; i < cadena.length(); i++) {
            System.out.println("Hola");
            c = cadena.charAt(i);
            switch (estado) {
                case 0:
                    if (c == '{') {
                        auxLexico += "{";
                        agregarToken(Token.TipoToken.Llave_izq, auxLexico, 0);
                    } else if (c == '}') {
                        auxLexico += "}";
                        agregarToken(Token.TipoToken.Llave_der, auxLexico, 0);
                    } else if (c == ';') {
                        auxLexico += ";";
                        agregarToken(Token.TipoToken.Punto_coma, auxLexico, 0);
                    } else if (c == ':') {
                        auxLexico += ":";
                        agregarToken(Token.TipoToken.Dos_puntos, auxLexico, 0);
                    } else if (c == ',') {
                        auxLexico += ",";
                        agregarToken(Token.TipoToken.Coma, auxLexico, 0);
                    } else if (c == '~') {
                        auxLexico += "~";
                        agregarToken(Token.TipoToken.Separador_conjuntos, auxLexico, 0);
                    } else if (c == '.') {
                        auxLexico += ".";
                        agregarToken(Token.TipoToken.Punto, auxLexico, 0);
                    } else if (c == '|') {
                        auxLexico += "|";
                        agregarToken(Token.TipoToken.Barra, auxLexico, 0);
                    } else if (c == '?') {
                        auxLexico += "?";
                        agregarToken(Token.TipoToken.Interrogante, auxLexico, 0);
                    } else if (c == '*') {
                        auxLexico += "*";
                        agregarToken(Token.TipoToken.Cerradura, auxLexico, 0);
                    } else if (c == '+') {
                        auxLexico += "+";
                        agregarToken(Token.TipoToken.Simbolo_Mas, auxLexico, 0);
                    } else if (c == '<') {
                        estado = 1;
                        auxLexico += "<";
                    } else if (c == '!') {
                        estado = 2;
                        auxLexico += "!";
                    } else if (c == '/') {
                        estado = 3;
                        auxLexico += "/";
                    } else if (c == '-') {
                        estado = 4;
                        auxLexico += "-";
                    } else if (c == '%') {
                        estado = 5;
                        auxLexico += "%";
                    } else if (Character.isLetter(c)) { //Para identificar id´s
                        estado = 6;
                        auxLexico += c;
                    } else if (c == '"') { //Para identificar el valor del lexema
                        estado = 7;
                        auxLexico += c;
                    } else if (c == '\n') { //Si es salto de línea no hago nada

                    } else if (c == ' ') { //Si es espacio no hago nada

                    } else if (c == '\t') //Si es tab
                    {

                    } else if (c == '\r') //Si es retorno de carro
                    {

                    }
                    break;

                case 1:
                    if (c == '!') {
                        auxLexico += "!";
                        agregarToken(Token.TipoToken.Inicio_comentario_multi, auxLexico, 0);
                    } else {
                        System.out.println("ERROR LEXICO");
                        auxLexico = "";
                        estado = 0;
                        i--;
                    }
                    break;

                case 2:
                    if (c == '>') {
                        auxLexico += ">";
                        agregarToken(Token.TipoToken.Fin_comentario_multi, auxLexico, 0);
                    } else {
                        i--;
                        System.out.println("ERROR LEXICO");
                        auxLexico = "";
                        estado = 0;
                    }
                    break;

                case 3:
                    if (c == '/') {
                        auxLexico += "/";
                        estado = 8;
                    } else {
                        i--;
                        System.out.println("ERROR LEXICO");
                        auxLexico = "";
                        estado = 0;
                    }
                    break;

                case 4:
                    if (c == '>') {
                        auxLexico += ">";
                        agregarToken(Token.TipoToken.Asignacion_flecha, auxLexico, 0);
                    } else {
                        i--;
                        System.out.println("ERROR LEXICO");
                        auxLexico = "";
                        estado = 0;
                    }
                    break;

                case 5:
                    if (c == '%') {
                        auxLexico += "%";
                        agregarToken(Token.TipoToken.Porcentaje_doble, auxLexico, 0);
                    } else {
                        i--;
                        System.out.println("ERROR LEXICO");
                        auxLexico = "";
                        estado = 0;
                    }
                    break;

                case 6:
                    if (Character.isLetter(c) || Character.isDigit(c)) {
                        auxLexico += c;
                        estado = 6;
                    } else if (auxLexico.equals("CONJ")) {
                        i--;
                        agregarToken(Token.TipoToken.Reservada_conj, auxLexico, estado);
                    } else {
                        i--;
                        agregarToken(Token.TipoToken.Id, auxLexico, estado);
                    }
                    break;

                case 7:
                    if (c == '"') {
                        auxLexico += c;
                        agregarToken(Token.TipoToken.Cadena, auxLexico, estado);

                    } else {
                        auxLexico += c;
                        estado = 7;
                    }
                    break;

                case 8:
                    if (c == '\n') {
                        agregarToken(Token.TipoToken.Comentario_una_linea, auxLexico, estado);
                    } else {
                        auxLexico += c;
                        estado = 8;
                    }
                    break;

            }

        }

        return listaTokens;
    }

    public void agregarToken(Token.TipoToken tipoToken, String auxLexico, int estado) {
        listaTokens.addLast(new Token(tipoToken, auxLexico, estado));
        this.estado = 0;
        this.auxLexico = "";
    }

    public void imprimir() {
        System.out.println(listaTokens.size());
        for (Token var : listaTokens) {
            System.out.println(var.getTipo());
            System.out.println(var.getLexema());
        }
    }

    public void vaciarLista() {
        this.listaTokens.clear();
    }

}
