package com.shoppertrak.service;
  
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.IllegalArgumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppertrak.dao.TrafficDao;
import com.shoppertrak.domain.TrafficRecord;
import com.shoppertrak.domain.TrafficRecordShort;

@Service
public class TrafficService {

        TrafficDao dao;
        final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

        @Autowired
        public TrafficService(TrafficDao dao) {
                this.dao = dao;
        }

        public TrafficRecord get(int id) {
                return dao.get(id);
        }

        public LinkedHashMap<String, Object> getByStoreId(int clientId, int storeId, String startTime, String endTime){

               Collection<TrafficRecord> rec = dao.getAll();
               boolean flag = (storeId==-1) ? true : false;
               SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
               Date dtStart = null;
               Date dtEnd = null;
               Calendar c = Calendar.getInstance();
               Map<Date, TrafficRecordShort> temp = new HashMap<Date, TrafficRecordShort>();
               LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

               try {
                        dtStart = df.parse(startTime);
                        dtEnd = df.parse(endTime);
                } catch (ParseException e) {}


               for (TrafficRecord item : rec) {
                   if (item!=null){
                     if (item.clientId==clientId && (flag || item.storeId == storeId ) &&
                          item.min5_dt.compareTo(dtStart)>-1 && item.min5_dt.compareTo(dtEnd)<1){
                          
                           long t = item.min5_dt.getTime();
                           item.min5_dt = new Date(t + (minutes(item.min5_dt) * ONE_MINUTE_IN_MILLIS));
                           TrafficRecordShort recTemp = temp.get(item.min5_dt);
                           if (recTemp!=null){
                                recTemp.setEnters(recTemp.enters+item.enters);
                                recTemp.setExits(recTemp.exits + item.exits);
                           } else {
                                 temp.put(item.min5_dt, new TrafficRecordShort(item.min5_dt, item.enters, item.exits));
                                }
                     }
                   }
                  }
             ArrayList<TrafficRecordShort> res = new ArrayList<TrafficRecordShort>();
             Date current = minDate(dtStart);

             while (current.compareTo(dtEnd)<1) {
                        TrafficRecordShort recTemp = temp.get(current);
                           if (recTemp!=null){
                                 res.add(recTemp);
                            } else {
                                 res.add(new TrafficRecordShort(current, 0, 0));
                                }
                        long t = current.getTime();
                        current = new Date(t + (15 * ONE_MINUTE_IN_MILLIS));
              }
             result.put("clientId", clientId);
             if (!flag) result.put("storeId", storeId);
             result.put("traffic", res);
             return result;
        }

        public void save(TrafficRecord r) {
                dao.save(r);
        }

        public Collection<TrafficRecord> getAll() {
                return dao.getAll();
        }

        public void delete(int id) {
                dao.delete(id);
         dao.delete(id);
        }

        public boolean verifyDateInput(String input) {
                final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmm");
                if (input != null) {
                        if (input.length()!=12|| !input.matches("[0-9]+")) return false;
                        try {
                                java.util.Date ret = sdf.parse(input.trim());
                                if (sdf.format(ret).equals(input.trim())) {
                                return true;
                                }
                        } catch (ParseException e) {
                                e.printStackTrace();
                        }
                }
                return false;
        }

        public int minutes(Date d){
                Calendar c = Calendar.getInstance();

                c.setTime(d);
                int minute = c.get(Calendar.MINUTE);
                int delta = 0;
                if (minute%5 !=0 ) throw new IllegalArgumentException();
                switch(minute){
                        case 5: delta = 10; break;
                        case 10: delta = 5; break;
                        case 20: delta = 10; break;
                        case 25: delta = 5; break;
                        case 35: delta = 10; break;
                        case 40: delta = 5; break;
                        case 50: delta = 10; break;
                        case 55: delta = 5;
                }
                return delta;

        }
        public Date minDate(Date d){
                Calendar c = Calendar.getInstance();

                c.setTime(d);
                int minute = c.get(Calendar.MINUTE);
                int delta = 0;
                if (minute == 0 || minute == 15 || minute==45 || minute ==30)
                        { delta = 15;} // return d;
                else if (minute<15) {delta = 15-minute;}
                else if (minute<30) {delta = 30-minute;}
                else if (minute<45) {delta = 45-minute;}
                else if (minute<60) {delta = 60-minute;}

                long t = d.getTime();
                return new Date(t + (delta * ONE_MINUTE_IN_MILLIS));
                 }

}

