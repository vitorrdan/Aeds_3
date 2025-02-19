package Estudos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Livro implements EntidadeArquivo {

    private int id;
    private String isbn;
    private String titulo;
    private String autor;
    private float preco;

    public Livro() throws Exception  {
        this(-1, "             ", "", "", 0F);
    }

    public Livro(String isbn, String titulo, String autor, float preco) throws Exception {
        this(-1, isbn, titulo, autor, preco);
    }

    public Livro(int id, String isbn, String titulo, String autor, float preco) throws Exception {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;

        if(!this.isbn.equals("") && !isValidISBN13(this.isbn))
            throw new Exception("ISBN inválido");
    } 

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getISBN() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public byte[] toByteArray() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.write(isbn.getBytes());
        dos.writeUTF(titulo);
        dos.writeUTF(autor);
        dos.writeFloat(preco);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] vb) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(vb);
        DataInputStream dis = new DataInputStream(bais);
        id = dis.readInt();
        byte[] aux = new byte[13];
        dis.read(aux);
        isbn = new String(aux);
        titulo = dis.readUTF();
        autor = dis.readUTF();
        preco = dis.readFloat();
    }




public static boolean isValidISBN13(String isbn) {
    System.out.println(isbn);
        // Remove qualquer caractere que não seja número
        isbn = isbn.replaceAll("[^0-9]", "");

        // Verifica se tem 13 dígitos
        if (isbn.length() != 13) {
            return false;
        }

        // Cálculo do dígito verificador (algoritmo ISBN-13)
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        int lastDigit = Character.getNumericValue(isbn.charAt(12));

        return checkDigit == lastDigit;
    }    
}