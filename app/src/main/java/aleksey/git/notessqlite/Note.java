package aleksey.git.notessqlite;

public class Note {
    private String note;
    private String time;
    private String id;

    public Note(String note, String time, String id){
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

    public String getId() {
        return this.id;
    }

    public void setImage(String id) {
        this.id = id;
    }
}
