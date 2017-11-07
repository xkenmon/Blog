package cn.xkenmon.blog.Throwable;

public class FileSizeTooLargeException extends Throwable {
    private long max;
    private long actSize;

    public FileSizeTooLargeException(long max, long actSize){
        this.max = max;
        this.actSize =actSize;
    }

    @Override
    public String toString() {
        return super.toString()+"\nFile is too large: max-"+ max + ",fileSize-" + actSize;
    }
}
