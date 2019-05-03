package li.sliit.alarm;

public class Alarm {

    private long id;
    private String name;
    private String time;
    private long alarmToneId;
    private boolean activated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getAlarmToneId() {
        return alarmToneId;
    }

    public void setAlarmToneId(long alarmToneId) {
        this.alarmToneId = alarmToneId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
