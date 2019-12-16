package application;

import java.util.List;

import org.cloudbus.cloudsim.CloudletScheduler;
import org.cloudbus.cloudsim.MyVm;
import org.hana.pso.Fog;

public class Machines {
	private long size;
	private int ram; 
	private String name;
	private int mips; 
	private long bw; 
	private int pesNumber; 
	private String c_name; 
	private double cost_hour; 
	private int id; 
	private int userId; 
	private String vmm;
	private String type;
	private CloudletScheduler cloudletScheduler;
	
	public Machines(int id,String name, int userId, int mips, int pesNumber, int ram,
			long bw, long size, String vmm, CloudletScheduler cloudletScheduler, double cost_hour, String type) {
		
		this.id=id;
		this.name=name;
		this.userId=userId;
		this.mips=mips;
		this.pesNumber=pesNumber;
		this.ram=ram;
		this.bw=bw;
		this.size=size;
		this.vmm=vmm;
		this.cloudletScheduler=cloudletScheduler;
		this.cost_hour = cost_hour;
		this.type=type;
	}
	
	
	public Machines(int id, String name,long size,  long bw, int mIPS, int rAM,
			 double cost,int pesNumber,String type) {
		super();
		this.id = id;
		this.name = name;
		this.size=size;
		this.bw=bw;
		mips = mIPS;
		ram = rAM;
		
		this.cost_hour=cost;
		this.pesNumber=pesNumber;
		this.type=type;
		
	}
	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public CloudletScheduler getCloudletScheduler() {
		return cloudletScheduler;
	}


	public void setCloudletScheduler(CloudletScheduler cloudletScheduler) {
		this.cloudletScheduler = cloudletScheduler;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getSize_c() {
		return size;
	}
	public void setSize_c(long size_c) {
		this.size = size_c;
	}
	public int getRam_c() {
		return ram;
	}
	public void setRam_c(int ram_c) {
		this.ram = ram_c;
	}
	public int getMips_c() {
		return mips;
	}
	public void setMips_c(int mips_c) {
		this.mips = mips_c;
	}
	public long getBw_c() {
		return bw;
	}
	public void setBw_c(long bw_c) {
		this.bw = bw_c;
	}
	public int getPesNumber() {
		return pesNumber;
	}
	public void setPesNumber(int pesNumber) {
		this.pesNumber = pesNumber;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public double getCost_hour_c() {
		return cost_hour;
	}
	public void setCost_hour_c(double cost_hour_c) {
		this.cost_hour = cost_hour_c;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return userId;
	}
	public void setUser_id(int user_id) {
		this.userId = user_id;
	}
	public String getVmm() {
		return vmm;
	}
	public void setVmm(String vmm) {
		this.vmm = vmm;
	} 
	
	
	
	

}
