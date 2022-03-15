package com.shoppertrak.domain;
  
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class TrafficRecordShort {
        public String min5_dt;
        public int enters;
        public int exits;

        public TrafficRecordShort(String min5_dt, int enters, int exits){
                this.min5_dt = min5_dt;
                this.enters = enters;
                this.exits = exits;
        }
        public TrafficRecordShort(Date min5_dt, int enters, int exits){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                this.min5_dt = df.format(min5_dt);
                this.enters = enters;
                this.exits = exits;
        }
        public TrafficRecordShort setMin5_dt(Date min5_dt) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                this.min5_dt = df.format(min5_dt);
                return this;
        }
        public TrafficRecordShort setMin5_dt(String min5_dt) {
                this.min5_dt = min5_dt;
                return this;
        }
        public TrafficRecordShort setEnters(int enters) {
                this.enters = enters;
                return this;
        }
        public TrafficRecordShort setExits(int exits) {
                this.exits = exits;
                return this;
        }
        public boolean equals(TrafficRecordShort that){
                if (this.min5_dt.equals(that.min5_dt) && this.enters == that.enters && this.exits == that.exits) return true;
                return false;
        }
        public int hashCode(){
           return this.min5_dt.hashCode()<<10 + this.enters<<5 + this.exits;
        }
}
