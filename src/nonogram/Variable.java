package nonogram;

import java.util.LinkedList;
import java.util.Stack;

public class Variable {

    private Stack<Integer> terms = new Stack<>();
    private LinkedList<String> domain = new LinkedList<>();
    int sortAssist[][];
    int length;
    private int domainSize;
    private int index;

    public Variable(String in, int length, int index) {
        this.index = index;
        this.length = length;
        String[] temp = in.split(" ");
        for (int i = temp.length - 1; i >= 1; i--) {
            terms.add(Integer.parseInt(temp[i]));
        }
        this.generateDomain(this.length, "");
        this.domainSize = this.domain.size();
        sortAssist = new int[domain.size()][2];
    }

    public Variable() {
        this.domainSize = Integer.MAX_VALUE;
    }

    private void generateDomain(int limit, String str) {
        int startPoint = length - limit;

        while (true) {
            String tempStr = str;
            int temp = terms.pop();
            if ((limit < temp) || (length - startPoint < temp)
                    || (!terms.isEmpty() && (length - startPoint - temp - 1 < terms.peek()))) {
                terms.add(temp);
                return;
            }

            for (int i = str.length(); i < startPoint; i++) {
                tempStr += ".";
            }

            for (int i = startPoint; i < temp + startPoint; i++) {
                tempStr += "*";
            }

            tempStr += ".";

            if(tempStr.length()>this.length){
                tempStr=tempStr.substring(0, this.length);
            }
            
            if (terms.isEmpty()) {
                for(int i=tempStr.length();i<this.length;i++){
                    tempStr += ".";
                }
                domain.add(tempStr);
            } else {
                generateDomain(length - 1 - temp - startPoint, tempStr);
            }
            startPoint++;
            terms.add(temp);

        }
    }

    public int getDomainSize() {
        return this.domainSize;
    }

    public String getDomain(int index) {
        return this.domain.get(index);
    }

    public int getIndex() {
        return this.index;
    }

    public void setDomain(LinkedList<String> dom) {
        this.domain = dom;
    }

    public LinkedList getDomainCollection() {
        return this.domain;
    }

    public void removeFromDomain(String str) {
        int i;
        for ( i = 0; i < domain.size(); i++) {
            if (domain.get(i).equals(str)) {
                domain.remove(i);
                break;
            }
        }
        this.domainSize=this.domain.size();
        for(int j=i;j<sortAssist.length-1;j++){
            sortAssist[j]=sortAssist[j+1];
        }
        sortAssist[sortAssist.length-1][1]=Integer.MAX_VALUE;
    }
    
    public void removeFromDomain(int i){
        this.domain.remove(i);
        this.domainSize=this.domain.size();
        for(int j=i;j<sortAssist.length-1;j++){
            sortAssist[j]=sortAssist[j+1];
        }
        sortAssist[sortAssist.length-1][1]=Integer.MAX_VALUE;
    }
    
    @Override
    public Variable clone(){
        Variable clone=new Variable();
        for(int i=0;i<this.domainSize;i++){
            clone.domain.add(this.domain.get(i));
        }
        clone.domainSize=clone.domain.size();
        clone.index=this.index;
        clone.length=this.length;
        clone.sortAssist=new int[clone.getDomainSize()][2];
//        for(int i=0;i<clone.getDomainSize())
        System.arraycopy(this.sortAssist, 0, clone.sortAssist, 0, this.domainSize);
        clone.terms=this.terms;
        return clone;
    }
}
