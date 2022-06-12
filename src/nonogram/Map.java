package nonogram;

import java.util.Arrays;
import java.util.LinkedList;

public class Map {

    LinkedList<Variable> columns;
    LinkedList<Variable> rows;
    int length;
    String[] map;

    public Map(LinkedList<Variable> columns, LinkedList<Variable> rows, int length) {
        this.length = length;
        this.columns = columns;
        this.rows = rows;
        this.map = new String[length];
    }

    public Map(Map map) {
        this.length = map.length;
        this.map = new String[length];
        this.columns = new LinkedList<>();
        this.rows = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            this.columns.add(map.columns.get(i).clone());
            this.rows.add(map.rows.get(i).clone());
        }
        System.arraycopy(map.map, 0, this.map, 0, length);
    }

    public boolean isComplete() {
        for (String str : this.map) {
            if (str == null || str.length() == 0) {
                return false;
            }
        }
        return true;
    }

    public Variable MRV() {
        Variable min = new Variable();
        for (int i = 0; i < rows.size(); i++) {
            if ((rows.get(i).getDomainSize() < min.getDomainSize())
                    && (this.map[i] == null || this.map[i].equals(""))) {
                min = rows.get(i);
            }
        }
        return min;
    }

    public void LCV(Variable var) {
        int varIndex = var.getIndex();
        for (int i = 0; i < var.getDomainSize(); i++) {
            String temp = var.getDomain(i);
            int count = 0;
            for (int j = 0; j < columns.size(); j++) {
                for (int k = 0; k < columns.get(j).getDomainSize(); k++) {
                    if (columns.get(j).getDomain(k).charAt(varIndex) != temp.charAt(j)) {
                        count++;
                    }
                }
            }
            var.sortAssist[i][0] = i;
            var.sortAssist[i][1] = count;
        }
        Arrays.sort(var.sortAssist, (int[] a, int[] b) -> Integer.compare(a[1], b[1]));
        LinkedList<String> tempDom = new LinkedList<>();
        for (int i = var.getDomainSize() - 1; i >= 0; i--) {
            tempDom.add(var.getDomain(var.sortAssist[i][0]));
        }
        var.setDomain(tempDom);
    }

    public boolean isValid(String val, int index) {
        String[] tempMap = new String[length];
        System.arraycopy(map, 0, tempMap, 0, length);
        tempMap[index] = val;
        for (int i = 0; i < length; i++) {
            boolean flag = false;
            for (int j = 0; j < columns.get(i).getDomainSize(); j++) {
                boolean flag2 = true;
                for (int k = 0; k < length; k++) {
                    if (tempMap[k] != null) {
                        if (tempMap[k].charAt(i) != columns.get(i).getDomain(j).charAt(k)) {
                            flag2 = false;
                            break;
                        }
                    }
                }
                if (flag2) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public void updateMap(String str, int index) {
        map[index] = str;
    }

    public void forwardCheck() {
        for (int i = 0; i < length; i++) {
            if (map[i] == null) {
                for (int j = 0; j < rows.get(i).getDomainSize(); j++) {
                    if (!this.isValid(rows.get(i).getDomain(j), i)) {
                        rows.get(i).removeFromDomain(j);
                    }
                }
            }
        }
    }

    public void makeArcConsistent() {
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.rows.get(i).getDomainSize(); j++) {
                this.updateMap(this.rows.get(i).getDomain(j), i);
                for (int k = 0; k < length; k++) {
                    boolean flag=false;
                    if (map[k] == null) {
                        for (int w = 0; w < rows.get(k).getDomainSize(); w++) {
                            if (this.isValid(rows.get(k).getDomain(w), k)) {
                                flag=true;
                                break;
                            }
                        }
                        if(!flag){
                            rows.get(i).removeFromDomain(j);
                            break;
                        }
                    }
                }
            }
            this.updateMap(null, i);
        }
    }

    public void print() {
        for (String str : this.map) {
            System.out.println(str);
        }
    }

}
