package net.scit.entity;


public class Patient { 
	private String patientId; 		// 환자번호 
	private String name; 			// 이름
	private int age; 				// 나이
	private Department part; 		// 진료코드(진료과목)
	private int period; 			// 입원기간 
	private int expenses; 			// 진찰비 
	private int hospitalBill ; 		// 입원비
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Patient(String patientId, String name, int age, Department part, int period) {
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.part = part;
		this.period = period;
	}
	
	public Patient(String patientId, String name, int age, Department part, int period, int expenses, int hospitalBill)
	{
		super();
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.part = part;
		this.period = period;
		this.expenses = expenses;
		this.hospitalBill = hospitalBill;
	}
	private int calcHospitalBill() {
		if(period<=3)
			hospitalBill = period * 30000;
		else if(period<10)
			hospitalBill = period * 25000;
		else if(period<15)
			hospitalBill = (int) (period * 25000 * 0.85);
		else if(period<20)
			hospitalBill = (int) (period * 25000 * 0.80);
		else if(period<30)
			hospitalBill = (int) (period * 25000 * 0.77);
		else if(period<100)
			hospitalBill = (int) (period * 25000 * 0.72);
		else
			hospitalBill = (int) (period * 25000 * 0.68);
		return hospitalBill;
	}
	
	
	private int calcExpenses() {
		if (age >= 50)
			this.expenses = 2300;
		else if (age >= 40)
			this.expenses = 4500;
		else if (age >= 30)
			this.expenses = 7000;
		else if (age >= 20)
			this.expenses = 8000;
		else if (age >= 10)
			this.expenses = 5000;
		else
			this.expenses = 7000;
		return expenses;
	}
	
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
		calcExpenses();
	}
	public Department getPart() {
		return part;
	}
	public void setPart(Department part) {
		this.part = part;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
		calcHospitalBill();
	}
	public int getExpenses() {
		return expenses;
	}
	public void setExpenses(int expenses) {
		this.expenses = expenses;
	}
	public int getHospitalBill() {
		return hospitalBill;
	}
	public void setHospitalBill(int hospitalBill) {
		this.hospitalBill = hospitalBill;
	}
	
	public int total() {
		return expenses + hospitalBill;
	}
	
	@Override
	public String toString() {
		return String.format("    %s\t  %4s\t  %4s\t  %,4d원\t %2d일 \t %,7d원\t%,7d원", 
				patientId, name, part.getDescription() , calcExpenses() , period, calcHospitalBill() , total());

	}
		
}	
