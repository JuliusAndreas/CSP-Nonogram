
package algorithms;

import nonogram.Map;
import nonogram.Variable;

/**
 *
 * @author Connor Lynch
 */
public class BackTracker {
    public static boolean backTrack(Map map){        
        boolean isSucceed=false;
        if(map.isComplete()){
            map.print();
            isSucceed=true;
            return isSucceed;
        }
        Variable var=map.MRV();
//        System.out.println(var.getIndex() + "," +var.getDomainSize());
        map.LCV(var);
        for(int i=0;i<var.getDomainSize();i++){
            String value=var.getDomain(i);
            if(map.isValid(value, var.getIndex())){
                Map backupMap=new Map(map);
                map.updateMap(value, var.getIndex());
//                map.forwardCheck();
                isSucceed=backTrack(map);
                if(isSucceed){
                    return isSucceed;
                }
                map=backupMap;
            }
        }
        return isSucceed;
        
    }
}
