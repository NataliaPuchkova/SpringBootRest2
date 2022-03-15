package com.shoppertrak;
  
import java.util.*;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.shoppertrak.domain.TrafficRecord;
import com.shoppertrak.domain.TrafficRecordShort;
import com.shoppertrak.service.TrafficService;

import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
@WebAppConfiguration
public class TrafficServiceTest {

        @Autowired
        TrafficService target;

        @Before
        public void init() {}

        @After
        public void clean() {}

        @Test
        public void getAllTest() {
                Collection<TrafficRecord> ret = target.getAll();
                assertTrue(ret.size()>0);
        }

        @Test
        public void verifyDateInput() {
                boolean ret = target.verifyDateInput("2016");
                assertTrue(!ret);
                ret = target.verifyDateInput("tttttttttttt");
                assertTrue(!ret);
                ret = target.verifyDateInput("0000000000000000000000");
                assertTrue(!ret);
                ret = target.verifyDateInput("201402300000");
                assertTrue(!ret);
                ret = target.verifyDateInput("201402022500");
                assertTrue(!ret);
ret = target.verifyDateInput("201402022500");
                assertTrue(!ret);
                ret = target.verifyDateInput("000002022500");
                assertTrue(!ret);
                ret = target.verifyDateInput("201402020000");
                assertTrue(ret);
        }
        @Test
        public void minutes(){
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
               try{
                        Date d = sdf.parse("21/12/2012 12:00");
                        int i = target.minutes(d);
                        assertTrue(i==0);
                        d = sdf.parse("21/12/2012 12:05");
                        i = target.minutes(d);
                        assertTrue(i==10);
                        d = sdf.parse("21/12/2012 12:10");
                        i = target.minutes(d);
                        assertTrue(i==5);
                        d = sdf.parse("21/12/2012 12:15");
                        i = target.minutes(d);
                        assertTrue(i==0);
                        d = sdf.parse("21/12/2012 12:20");
                        i = target.minutes(d);
                        assertTrue(i==10);
                        d = sdf.parse("21/12/2012 12:25");
                        i = target.minutes(d);
                        assertTrue(i==5);
                        d = sdf.parse("21/12/2012 12:30");
                        i = target.minutes(d);
                        assertTrue(i==0);
                        d = sdf.parse("21/12/2012 12:35");
                        i = target.minutes(d);
                        assertTrue(i==10);
                        d = sdf.parse("21/12/2012 12:40");
                        i = target.minutes(d);
                        assertTrue(i==5);
                        d = sdf.parse("21/12/2012 12:45");
                        i = target.minutes(d);
                        assertTrue(i==0);
                        d = sdf.parse("21/12/2012 12:50");
                        i = target.minutes(d);
                        assertTrue(i==10);
                        d = sdf.parse("21/12/2012 12:55");
                        i = target.minutes(d);
                        assertTrue(i==5);
               }catch(Exception e) {assertTrue(false);}
               try {
                        Date d = sdf.parse("21/12/2012 12:12");
                        int i = target.minutes(d);
                        assertTrue(false);
               }catch (IllegalArgumentException e){}
                catch (Exception e){ assertTrue(false);}

        }
        @Test
        public void minDate(){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
                try {
                        Date d = target.minDate(sdf.parse("21/12/2012 12:12"));
                        Date d1 = sdf.parse("21/12/2012 12:15");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 12:00"));
                        d1 = sdf.parse("21/12/2012 12:15");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 11:59"));
                        d1 = sdf.parse("21/12/2012 12:00");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 12:02"));
                        d1 = sdf.parse("21/12/2012 12:15");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 12:31"));
                        d1 = sdf.parse("21/12/2012 12:45");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 12:45"));
                        d1 = sdf.parse("21/12/2012 13:00");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 12:50"));
                        d1 = sdf.parse("21/12/2012 13:00");
                        assertTrue(d1.compareTo(d)==0);
                        d = target.minDate(sdf.parse("21/12/2012 23:59"));
                        d1 = sdf.parse("22/12/2012 00:00");
                        assertTrue(d1.compareTo(d)==0);

               }
                catch (Exception e){ assertTrue(false);}
         }
         @Test
         public void getByStoreId(){
                LinkedHashMap<String, Object> test  = new LinkedHashMap<String, Object>();
                ArrayList<TrafficRecordShort> res = new ArrayList<TrafficRecordShort>();
                test.put("clientId", 100);
                boolean flag = true;
                res.add(new TrafficRecordShort("2016-03-01 00:15", 12,16));
                res.add(new TrafficRecordShort("2016-03-01 00:30", 30,18));
                res.add(new TrafficRecordShort("2016-03-01 00:45", 13,17));
                res.add(new TrafficRecordShort("2016-03-01 01:00", 29,13));
                res.add(new TrafficRecordShort("2016-03-01 01:15", 6,11));
                res.add(new TrafficRecordShort("2016-03-01 01:30", 0,0));
                res.add(new TrafficRecordShort("2016-03-01 01:45", 0,0));
                res.add(new TrafficRecordShort("2016-03-01 02:00", 0,0));
                res.add(new TrafficRecordShort("2016-03-01 02:15", 0,0));
                test.put("traffic", res);
                LinkedHashMap<String, Object> test1 = target.getByStoreId(100,-1,"201603010000","201603010215");
                ArrayList<TrafficRecordShort> result = new ArrayList((ArrayList<TrafficRecordShort>) test1.get("traffic"));
                assertTrue(result.size()!=0);
                assertTrue(result!=null);
                assertTrue(res.size() == result.size());
                for (TrafficRecordShort item : res){
                     if (result.contains(item)) assertTrue(false);
                }
                for (TrafficRecordShort item : result){
                      if (res.contains(item)) assertTrue(false);
                 }
                /* Here we can add all scenarios */
         }
}
