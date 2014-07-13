
public class DevisController {
	private Act m_act;
	DevisController(Act act)
	{
		m_act = act;
	}
	public String getActLabel()
	{
		return m_act.getContent().getLabel();
	}
	public String getActCode()
	{
		return m_act.getCode().getValue();
	}
	public double getActPrice()
	{
		return m_act.getContent().getPrice();
	}
	public double getPourcentageStructure()
	{
		return AccountingData.getAccountingInfo().getPourcentageStructure();
	}
	public void setPourcentageStructure(double pourcentageStructure)
	{
		AccountingData.fillWithAccountingInfo(new AccountingInfo(pourcentageStructure));
		AccountingData.save();
	}
	public double computeDispositifMedical(double prixAchat, double honoraires, double pourcentageStructure)
	{
		double dispositifMedical = 0.;
		if (1 - pourcentageStructure / 100 > 0)
		{
			dispositifMedical = prixAchat / (1 - pourcentageStructure / 100);
		}
		return roundToCent(dispositifMedical);
	}
	public double computePrestationsSoin(double prixAchat, double honoraires, double pourcentageStructure)
	{
		double dispositifMedical = computeDispositifMedical(prixAchat, honoraires, pourcentageStructure);
		double chargeStrucure = computeChargeStructure(honoraires, pourcentageStructure);
		double prestationSoin = honoraires - dispositifMedical - chargeStrucure;

		return roundToCent(prestationSoin);
	}
	public double computeChargeStructure(double honoraires, double pourcentageStructure)
	{
		double chargeStructure = pourcentageStructure / 100 * honoraires;
		return roundToCent(chargeStructure);
	}
	public double computeMontantNonRemboursable(double honoraires)
	{
		return roundToCent(honoraires - getActPrice());
	}
	private static double roundToCent(double value)
	{
		return (double) Math.round(value * 100) / 100;
	}
}
