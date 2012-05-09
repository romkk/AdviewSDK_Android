

package com.adview.obj;

public class Ration implements Comparable<Ration> {
    public String nid = "";
    public int type = 0;
    public String name = "";
    public double weight = 0;
    public String key = "";
    public String key2 = "";
    public String key3="";
    public int type2=1;
    public int priority = 0;
    public String logo="";
    
    public Ration() {}

	public int compareTo(Ration another) {
		int otherPriority = another.priority;
		if(this.priority < otherPriority) {
			return -1;
		}
		else if(this.priority > otherPriority) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
