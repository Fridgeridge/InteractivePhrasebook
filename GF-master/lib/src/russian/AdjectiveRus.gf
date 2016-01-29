
--# -path=.:../abstract:../common:../../prelude


concrete AdjectiveRus of Adjective = CatRus ** open ResRus, Prelude in {
flags  coding=utf8 ;

  lin

    PositA  a = { s = a.s!Posit; p = False};
       -- Comparative forms are used with an object of comparison, as
        -- adjectival phrases ("больше тебя").

    ComparA bolshoj tu =
          {s = \\af => bolshoj.s ! Compar ! af ++ tu.s ! (mkPronForm Gen Yes NonPoss) ; 
            p = True
           } ;

  ---- AR 17/12/2008
    UseComparA bolshoj =
          {s = \\af => bolshoj.s ! Compar ! af ;
            p = True
           } ;

  ---- AR 17/12/2008
    CAdvAP ad ap np = let adp = ad.s in  {  ---- should be ad.p
      s = \\af => ad.s ++ ap.s ! af ++ adp ++ np.s  ! (mkPronForm Gen Yes NonPoss) ; 
      p = True ----?
      } ;

  ---- AR 17/12/2008
    AdjOrd  a = {
      s = a.s ;
      p = True ---- ?
      } ;

-- $SuperlA$ belongs to determiner syntax in $Noun$.

    ComplA2 vlublen tu =
    {s = \\af => vlublen.s !Posit! af ++ vlublen.c2.s ++ 
          tu.s ! (mkPronForm vlublen.c2.c No NonPoss) ;
     p = True
    } ;

    ReflA2 vlublen = 
    {s = \\af => vlublen.s !Posit! af ++ vlublen.c2.s ++ sam.s ! vlublen.c2.c;
     p = True
    } ;

    SentAP vlublen sent= 
    {s = \\af => vlublen.s ! af ++ [", "] ++ sent.s;
      p = True
    } ;


    AdAP ada ap = {
      s = \\af => ada.s ++ ap.s ! af ;
      p = True
      } ;

    UseA2 a = {
      s = \\af => a.s ! Posit ! af ;
      p = True
    } ;
}

