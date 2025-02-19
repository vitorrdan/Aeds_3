package Estudos;

import java.io.*;

public class Arquivo {

    public String nomeEntidade;
    RandomAccessFile arquivo;
    final int TAMANHO_CABECALHO = 13;

    public Arquivo(String ne) throws Exception {
        this.nomeEntidade = ne;
        File f = new File(".//dados");
        if(!f.exists())
            f.mkdir();
        f = new File(".//dados//"+nomeEntidade);
        if(!f.exists())
            f.mkdir();
        arquivo = new RandomAccessFile(".//dados//"+nomeEntidade+"//"+nomeEntidade +".db", "rw");
        if(arquivo.length()<TAMANHO_CABECALHO) {
            arquivo.writeByte(1);  // versão do Arquivo
            arquivo.writeInt(0);   // último ID
            arquivo.writeLong(-1);   // ponteiro para primeiro registro excluído
        }
    }

    public int create(Livro entidade) throws Exception {
        // Obtem o ID da nova entidade
        arquivo.seek(1);
        int novoID = arquivo.readInt() + 1;
        entidade.setID(novoID);
        arquivo.seek(1);
        arquivo.writeInt(novoID);

        // Grava o novo registro
        arquivo.seek(arquivo.length());
        arquivo.writeByte(' ');
        byte[] vb = entidade.toByteArray();
        arquivo.writeShort(vb.length);
        arquivo.write(vb);

        return novoID;
    }

    public Livro read(String isbn) throws Exception {
        arquivo.seek(TAMANHO_CABECALHO);
        while(arquivo.getFilePointer() < arquivo.length()) {
            byte lapide = arquivo.readByte();
            short tamanho = arquivo.readShort();
            if(lapide == ' ') {
                byte[] dados = new byte[tamanho];
                arquivo.read(dados);
                Livro entidade = new Livro();
                entidade.fromByteArray(dados);
                if(isbn.equals(entidade.getISBN()))
                    return entidade;
            } else {
                arquivo.skipBytes(tamanho);
            }
        }
        return null;
    }
    
}

