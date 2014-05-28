
public class RepartitionController {
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
		return (double) Math.round(dispositifMedical * 100) / 100;
	}
	public double computePrestationsSoin(double prixAchat, double honoraires, double pourcentageStructure)
	{
		double dispositifMedical = computeDispositifMedical(prixAchat, honoraires, pourcentageStructure);
		double chargeStrucure = computeChargeStructure(honoraires, pourcentageStructure);
		double prestationSoin = honoraires - dispositifMedical - chargeStrucure;

		return (double) Math.round(prestationSoin * 100) / 100;
	}
	public double computeChargeStructure(double honoraires, double pourcentageStructure)
	{
		double chargeStructure = pourcentageStructure / 100 * honoraires;
		return (double) Math.round(chargeStructure * 100) / 100;
	}
}
