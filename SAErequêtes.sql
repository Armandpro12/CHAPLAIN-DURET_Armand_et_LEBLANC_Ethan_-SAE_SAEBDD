/* -----------------------------------------------------------------------
SAE requêtes
Version : 2.0
Auteur : Ethan LEBLANC Armand CHAPLAIN-DURET
Date création : 28/03/2022
------------------------------------------------------------------------- */
	

--Concernant notre ancienne idée de modélisation de la base de données demandée.On avait choisi les tables suivantes : 
--table critère
--table pays
--table continent
--table posseder
--table date-observée
--Les inconvénients etaient :
-- _pas assez de données donc tables inutiles pour continent, pays, critère et date_observée
-- _trop de données pour la table posseder qui prenait les identifiants de toutes les tables plus les données comme attributs
--Il a fallu opter pour une division de la table critère en plusieurs tables selon les critères par regroupement et plus d'attribut dans la table pays. Ainsi, on a supprimé les tables continent et date_observée.


--Requêtes : --Voici les fonctions qui exploitent toute la base de données :

--1
--Spécification : Renvoie la moyenne selon le pays, le critère (avec possiblité de précision) et le critère global entrés en paramètre.
create or replace function moy_pays (pays1 varchar, critère1 varchar, tableCritère varchar) returns numeric AS $$
declare
	moy1 numeric := 0;
begin
	if $1 is null or critère1 is null or tableCritère is null then
		RAISE NOTICE 'Pas de paramètres ou un paramètre manquant';
	end if;
	perform * from owid_covid_data.pays where nomPays = pays1;
	if (not found) then
		RAISE NOTICE 'Le pays que vous avez entré en paramère n a pas été inserer dans cette base de données';
	else 
		execute 'select avg(stats_' || critère1 || ') from owid_covid_data.' || tableCritère into moy1;
	end if;
	return moy1;
end;
$$ LANGUAGE 'plpgsql';

select moy_pays ('Africa', 'nouveaux_morts_smoothed', 'nouveaux_morts'); --test de la fonction.

--2.
--Spécification : Renvoie un int (1 ou 0) qui vérifie l'existance du pays entré en paramètre dans la base de données. 1 pour non trouvé, 0 pour trouvé.
create or replace function paysExiste (pays1 varchar) returns int AS $$

begin
	perform * from owid_covid_data.pays where nomPays = pays1;
	if (not found) then 
		return 1;
	else
		return 0;
	end if;
end;
$$ LANGUAGE 'plpgsql';

select paysExiste('Africa');


create or replace function dateExiste (date1 date) returns int AS $$

begin
	perform * from owid_covid_data.nouveaux_morts where date_nouveaux_morts = date1; --nouveaux_morts est choisi comme table car toutes les autres tables de critères ont les mêmes dates.
	if (not found) then 
		return 1;
	else
		return 0;
	end if;
end;
$$ LANGUAGE 'plpgsql';

select dateExiste('2020-07-12');--test de la fonction
--3.
--Spécification : Renvoie un int (1 ou 0) qui vérifie l'existance de la date entré en paramètre dans la base de données. 1 pour non trouvé, 0 pour trouvé.

--4.
--Spécification : Renvoie la moyenne selon le pays, le critère (avec possiblité de précision), le critère global et la date entrés en paramètre.
create or replace function moy_paysDate (pays1 varchar, critère1 varchar, tableCritère varchar, date1 date) returns numeric AS $$
declare
	moy1 numeric := 0;
	testCritère record;
	testTableCritère record;
	
	
begin
	if pays1 is null or critère1 is null or tableCritère is null or date1 is null then
		RAISE NOTICE 'Pas de paramètres ou un paramètre manquant';
	end if;
	if (paysExiste(pays1) = 1) or (dateExiste(date1) = 1) then
		RAISE NOTICE 'Le pays ou la date que vous avez entré en paramère n a pas été inserer dans cette base de données.';
	end if;
	execute 'select * from owid_covid_data.' || TableCritère into testTableCritère; --verification de la table du critère global.
	execute 'select avg(stats_' || critère1 || ') from owid_covid_data.' || tableCritère into testCritère;
	if (testCritère is null) or (testTableCritère is null) then
		RAISE NOTICE 'Le critère entré en paramètre n existe pas';
	else 
		execute 'select stats_' || critère1 || ' from owid_covid_data.' || tableCritère || ' inner join owid_covid_data.pays using (iso_codePays) where nomPays = ''' || pays1 || ''' and date_' || tableCritère || ' = ''' || date1 || '''' into moy1;
	
	return moy1;
	end if;
end;
$$ LANGUAGE 'plpgsql';

select moy_paysDate ('Africa', 'nouveaux_morts_smoothed', 'nouveaux_morts', '2020-09-23');--test de la fonction.
--select moy_paysDate (null, 'nouveaux_morts_smoothed', 'nouveaux_morts', '2020-09-23'); --test echec






