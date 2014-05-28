
public class AccountingInfo {
	private double pourcentageStructure;

	public AccountingInfo(double pourcentageStructure) {
		this.pourcentageStructure = pourcentageStructure;
	}

	public AccountingInfo() {
		this.pourcentageStructure = 0.;
	}

	public AccountingInfo(AccountingInfo other) {
		this.pourcentageStructure = other.pourcentageStructure;
	}

	public double getPourcentageStructure() {
		return pourcentageStructure;
	}

	public void setPourcentageStructure(double pourcentageStructure) {
		this.pourcentageStructure = pourcentageStructure;
	}
	
}
