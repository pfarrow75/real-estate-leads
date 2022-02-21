package scott;

public class OldLead implements Comparable<OldLead>{
	
	private String[] lead;
	private String fullname;
	private String address;
	private String maddress;
	
	public OldLead(String[] s) {
		this.lead = s;
		this.fullname = s[0] + " " + s[1];
		this.maddress = s[2];
		if(this.maddress == null) {
			this.maddress = "empty address";
		}
	}

	public String getName() {
		return fullname;
		
	}
	
	public String getAddress() {
		return maddress;
	}
	
	public String getString() {
		String l = "";
		for(int i = 0; i < lead.length -2; i++) {
			l += lead[i] + ",";
		}
		l = l + lead[lead.length -1];
		return l;
	}
	@Override
	public int compareTo(OldLead o) {
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
