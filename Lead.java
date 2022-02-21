package scott;

public class Lead implements Comparable<Lead>{
	private String[] lead;
	private String first;
	private String last;
	private String fullname;
	private String address;
	private String maddress;
	
	public Lead(String[] s) {
		this.lead = s;
		this.first = s[0];
		this.last = s[1];
		this.address = s[2];
		this.fullname = s[0] + " " + s[1];
		this.maddress = s[6];
	}

	public String getName() {
		return fullname;
		
	}
	
	public String getAddress() {
		return maddress;
	}
	
	public String getString() {
		String l = "";
		for(int i = 0; i < lead.length -1; i++) {
			l += lead[i] + ",";
		}
		l = l + lead[lead.length -1] + "\n";
		return l;
	}
	
	@Override
	public int compareTo(Lead o) {
		// TODO Auto-generated method stub
		if((this.fullname).equalsIgnoreCase(o.fullname)) {
			if((this.maddress).equalsIgnoreCase(o.maddress)){
				return 0;
			}
			else {
				return (this.maddress).compareTo(o.maddress);
			}
		}
		return (this.fullname).compareTo(o.fullname);
	}

	
}
