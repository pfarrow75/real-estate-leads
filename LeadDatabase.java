package scott;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LeadDatabase {
	private File writer;
	private ArrayList<Lead> newLeads;
	private ArrayList<OldLead> oldLeads;
	private ArrayList<Lead> duplicateNewLeads;
	private ArrayList<Lead> neededLeads;
	
	public ArrayList<Lead> readLeadFile(String filename){
		try {
			newLeads = new ArrayList<Lead>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			br.readLine();
			String s = br.readLine(); //reads header and sets up
			int i = 0;
			while(i < 19641) {
				String[] l = s.split(",");
				
				Lead x = new Lead(l);
				newLeads.add(x);
				s = br.readLine();
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return newLeads;
	}
	
	public ArrayList<OldLead> readOldLeadFile(String filename){
		try {
			oldLeads = new ArrayList<OldLead>();
			BufferedReader br = new BufferedReader(new FileReader(filename));
			br.readLine();
			String s = br.readLine(); //reads header and sets up
			int i = 0;
			while(s != null) {
				String[] l = s.split(",");
				
				OldLead x = new OldLead(l);
				oldLeads.add(x);
				s = br.readLine();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		oldLeads.remove(oldLeads.size() -1);
		return oldLeads;
	}

	public void writeToCSVFile(String Filename, ArrayList<Lead> l) {
		writer = new File(Filename);
		try {
			FileWriter fw = new FileWriter(writer, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Primary First,Primary Last,Address,City,State,ZIP,Mail Address,Mail City,Mail State,Mail ZIP,Primary Name\n");
			for(int i = 0; i < l.size(); i++) {
				bw.write(l.get(i).getString());
			}
			bw.close();
			fw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	public ArrayList<OldLead> removeOldDuplicates(ArrayList<OldLead> s, int size) {
		LeadDatabase l = new LeadDatabase();
		ArrayList<OldLead> holder = new ArrayList<OldLead>();
		for(int i = 0; i < s.size(); i++) {
			if((s.get(i).getName()).equals(" ")) { //removes no name leads
				
			}
			else if(i > 1) {
				if(s.get(i).getName().equalsIgnoreCase(s.get(i-1).getName()) && s.get(i).getAddress().equalsIgnoreCase(s.get(i-1).getAddress())) {
				}
				else if(s.get(i).getName().equalsIgnoreCase(s.get(i-2).getName()) && s.get(i).getAddress().equalsIgnoreCase(s.get(i-2).getAddress())) {
				}
				else {
					holder.add(s.get(i));
				}
			}
			else{
				holder.add(s.get(i));
			}
		}
		if(size > holder.size()){
			return l.removeOldDuplicates(holder, holder.size());
		}
		return holder;
	}
	
	
	
	public ArrayList<Lead> removeDuplicates(ArrayList<Lead> duplicates, ArrayList<Lead> s, int size) {
		LeadDatabase l = new LeadDatabase();
		duplicateNewLeads = duplicates;
		ArrayList<Lead> holder = new ArrayList<Lead>();
		for(int i = 0; i < s.size(); i++) {
			if((s.get(i).getName()).equals(" ")) { //removes no name leads
				
			}
			else if(i > 1) {
				if(s.get(i).getName().equalsIgnoreCase(s.get(i-1).getName()) && s.get(i).getAddress().equalsIgnoreCase(s.get(i-1).getAddress())) {
					duplicateNewLeads.add(s.get(i));
				}
				else if(s.get(i).getName().equalsIgnoreCase(s.get(i-2).getName()) && s.get(i).getAddress().equalsIgnoreCase(s.get(i-2).getAddress())) {
					duplicateNewLeads.add(s.get(i));
				}
				else {
					holder.add(s.get(i));
				}
			}
			else{
				holder.add(s.get(i));
			}
		}
		if(size > holder.size()){
			return l.removeDuplicates(duplicateNewLeads, holder, holder.size());
		}
		return holder;
	}
	
	
	public ArrayList<Lead> crossCheckDuplicates(ArrayList<Lead> s, ArrayList<OldLead> t){
		neededLeads = new ArrayList<Lead>();
		
		int i = 0;
		int j = 0;
		
		while(i < s.size() && j < t.size()) {
			if(j == t.size() -1) {
				for(i = i; i < s.size(); i++) {
					neededLeads.add(s.get(i));
				}
				return neededLeads;
			}
			if(i == s.size() -1) {
				return neededLeads;
			}
			if((s.get(i)).getName().compareTo((t.get(j).getName())) < 0) {
				neededLeads.add(s.get(i));
				i++;
			}
			if((s.get(i)).getName().compareTo((t.get(j).getName())) > 0) {
				j++;
			}
			if((s.get(i)).getName().compareTo((t.get(j).getName())) == 0) {
				if((s.get(i)).getAddress().compareTo((t.get(j).getAddress())) < 0) {
					neededLeads.add(s.get(i));
					i++;
				}
				if((s.get(i)).getAddress().compareTo((t.get(j).getAddress())) > 0) {
					j++;
				}
				if((s.get(i)).getAddress().compareTo((t.get(j).getAddress())) == 0) {
					i++;

				}
			}
			
		}
		
		
		return neededLeads;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		LeadDatabase l = new LeadDatabase();
		ArrayList<OldLead> checker = l.readOldLeadFile("Fresno.csv");
		ArrayList<Lead> duplicates = new ArrayList<Lead>();
		Collections.sort(checker);
		checker = l.removeOldDuplicates(checker, checker.size());
		
		ArrayList<Lead> test = l.readLeadFile("Kings.csv"); 
		Collections.sort(test);
		test = l.removeDuplicates(duplicates, test, test.size());
		test = l.crossCheckDuplicates(test, checker);
		
		l.writeToCSVFile("Kings Tulare Kern County NonOwner occupied Over 50.xlsx - Sheet1_filtered.csv", test);
		l.writeToCSVFile("Kings Tulare Kern County NonOwner occupied Over 50.xlsx - Sheet1_duplicates.csv", duplicates);
		


	}

}
