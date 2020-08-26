package aleksey.git.notessqlite;

public class Note {
    private String note;
    private String time;
    private int id;

    public Note(String note, String time, int id){
        this.note=note;
        this.time = time;
        this.id = id;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return this.id;
    }

    public void setImage(int id) {
        this.id = id;
    }
}
