package net.scit.entity;

public enum Department {
	MI(1, "�ܰ�"), NI(2, "����"), SI(3, "�Ǻΰ�"), TI(4, "�Ҿư�"), VI(5, "����ΰ�"), WI(6, "�񴢱��");

	private int code;
	private String description;

	 Department(int code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    public String getDescription() {
	        return this.description;
	    }

	    public int getCode() {
	        return code;
	    }

	    public static Department of(int code) {
	        for (Department department : Department.values()) {
	            if (department.getCode() == code) {
	                return department;
	            }
	        }
	        //ã�� �ڵ尡 ���� ���
	        throw new IllegalArgumentException("���� ��ȣ�� �ùٸ��� �ʽ��ϴ�.");
	    }

}
