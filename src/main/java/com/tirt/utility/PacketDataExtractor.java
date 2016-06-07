package com.tirt.utility;

import com.tirt.entity.Point;
import org.pcap4j.packet.AbstractPacket;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 03.06.2016.
 */
public class PacketDataExtractor {

    public static List<Point> extractSthAndSth(List<Packet> packets, List<Long> timestamps) {
        List<Point> points = new ArrayList<>();

        for(int i=0; i<packets.size(); i++) {
            double secondAttribute = 0;

            double firstAttribute;
            if(packets.get(i).getPayload().getPayload() != null)
                if(packets.get(i).getPayload().getPayload().getPayload() != null)
                    firstAttribute = packets.get(i).getPayload().getPayload().getPayload().length();
                else
                    firstAttribute = 0;
            else
                firstAttribute = 0;
            /*System.out.println("Dane: ");
            for(int j = 0; j < packets.get(i).getHeader().getRawData().length; j++){
                System.out.print(packets.get(i).getHeader().getRawData()[j] + ";");
            }*/

            if(i == 0) {
                secondAttribute = timestamps.get(1) - timestamps.get(0);
            }
            else {
                secondAttribute = timestamps.get(i) - timestamps.get(i-1);
            }



//            byte[] bytes = packet.getHeader().getRawData();
//            System.out.println(((AbstractPacket.AbstractHeader)packet.getHeader()).toHexString());

            //System.out.println(firstAttribute + ", " + secondAttribute);

            points.add(new Point(firstAttribute, secondAttribute));

        }

        return points;
    }
    public static int integerfrmbinary(String str){
        double j=0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)== '1'){
                j=j+ Math.pow(2,str.length()-1-i);
            }

        }
        return (int) j;
    }
    
}
