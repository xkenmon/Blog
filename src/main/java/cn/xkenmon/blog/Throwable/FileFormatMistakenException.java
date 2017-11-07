package cn.xkenmon.blog.Throwable;

public class FileFormatMistakenException extends Throwable {
    private String format;

    public FileFormatMistakenException(String format){
        this.format = format;
    }

    @Override
    public String toString() {
        return super.toString()+"\nfile format not support:" + format;
    }
}
