package Estudos;

public interface EntidadeArquivo {
    public void setID(int id);
    public int getID();
    public byte[] toByteArray() throws Exception;
    public void fromByteArray(byte[] vb) throws Exception;
}
