--# -coding=cp1251
concrete AdjectiveBul of Adjective = CatBul ** open ResBul, Prelude in {
  flags coding=cp1251 ;

  lin
    PositA  a = {
      s = \\aform => a.s ! aform ;
      adv = a.adv ;
      isPre = True
      } ;

    ComparA a np = {
      s = \\aform => "��" ++ hyphen ++ a.s ! aform ++ "��" ++ np.s ! RObj Acc ; 
      adv = "��" ++ hyphen ++ a.adv ++ "��" ++ np.s ! RObj Acc ;
      isPre = True
      } ;
    UseComparA a = {
      s = \\aform => "��" ++ hyphen ++ a.s ! aform ; 
      adv = "��" ++ hyphen ++ a.adv ;
      isPre = True
      } ;

    AdjOrd ord = {
      s = ord.s ;
      adv = ord.s ! ASg Neut Indef ; 
      isPre = True
      } ;

-- $SuperlA$ belongs to determiner syntax in $Noun$.

---- just to make the API compile. AR 7/4/2010
    CAdvAP ad ap np = {
      s = \\a => ad.s ++ ap.s ! a ++ ad.sn ++ np.s ! RObj Acc ; 
      adv =      ad.s ++ ap.adv ++ ad.sn ++ np.s ! RObj Acc ; 
      isPre = False
      } ;

    ComplA2 a np = {
      s = \\aform => a.s ! aform ++ a.c2 ++ np.s ! RObj Acc ; 
      adv = a.adv ++ a.c2 ++ np.s ! RObj Acc ; 
      isPre = False
      } ;

    ReflA2 a = {
      s = \\aform => a.s ! aform ++ a.c2 ++ ["���� ��"] ; 
      adv = a.adv ++ a.c2 ++ ["���� ��"] ; 
      isPre = False
      } ;

    SentAP ap sc = {
      s = \\a => ap.s ! a ++ sc.s ;
      adv = ap.adv ++ sc.s ;
      isPre = False
      } ;

    AdAP ada ap = {
      s = \\a => ada.s ++ ap.s ! a ;
      adv = ada.s ++ ap.adv ;
      isPre = ap.isPre
      } ;

    UseA2 a = {
      s = a.s ; 
      adv = a.adv ; 
      isPre = True
      } ;
      
    AdvAP ap adv = {
      s = \\aform => ap.s ! aform ++ adv.s ;
      adv = ap.adv ++ adv.s;
      isPre = False
    } ;

}
