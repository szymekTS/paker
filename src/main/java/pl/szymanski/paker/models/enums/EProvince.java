package pl.szymanski.paker.models.enums;

public enum EProvince {
	PROV_DOLNOSLASKIE("dolnośląskie"), PROV_KUJAWSKOPOMORSKIE("kujawsko-pomorskie"), PROV_LUBELSKIE("lubelskie"),
	PROV_LUBUSKIE("lubuskie"), PROV_LODZKIE("łódzkie"), PROV_MALOPOLSKIE("małopolskie"),
	PROV_MAZOWIECKIE("mazowieckie"), PROV_OPOLSKIE("opolskie"), PROV_PODKARPACKIE("podkarpackie"),
	PROV_PODLASKIE("podlaskie"), PROV_POMORSKIE("pomorskie"), PROV_SLASKIE("śląskie"),
	PROV_SWIETOKRZYSKIE("świętokrzyskie"), PROV_WARMINSKOMAZURSKIE("warmińsko-mazurskie"),
	PROV_WIELKOPOLSKIE("wielkopolskie"), PROV_ZACHODNIOPOMORSKIE("zachodniopomorskie");

	public final String name;

	private EProvince(String name) {
		this.name = name;
	}

}