package mirea.mobile.kamilla.reviews;

import java.util.Date;

public class Booking {
    private String room_type;
    private String guests_number;
    private Date date_in;
    private Date date_out;
    private String child_bed;

    public Booking() {
    }

    public Booking(String room_type, String guests_number, Date date_in, Date date_out, String child_bed) {
        this.setRoom_type(room_type);
        this.setGuests_number(guests_number);
        this.setDate_in(date_in);
        this.setDate_out(date_out);
        this.setChild_bed(child_bed);
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getGuests_number() {
        return guests_number;
    }

    public void setGuests_number(String guests_number) {
        this.guests_number = guests_number;
    }

    public Date getDate_in() {
        return date_in;
    }

    public void setDate_in(Date date_in) {
        this.date_in = date_in;
    }

    public Date getDate_out() {
        return date_out;
    }

    public void setDate_out(Date date_out) {
        this.date_out = date_out;
    }

    public String getChild_bed() {
        return child_bed;
    }

    public void setChild_bed(String child_bed) {
        this.child_bed = child_bed;
    }
}