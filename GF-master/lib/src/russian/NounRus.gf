--# -path=.:../abstract:../common:../../prelude

concrete NounRus of Noun = CatRus ** open ResRus, Prelude, MorphoRus in {

  flags optimize=all_subs ; coding=utf8 ;

  lin
    DetCN kazhduj okhotnik = {
      s = \\c => case kazhduj.size of {
	nom =>
              kazhduj.s ! AF (extCase c) okhotnik.anim (gennum okhotnik.g Sg) ++ 
              okhotnik.s ! NF kazhduj.n (extCase c) nom  ;
	nompl =>
              kazhduj.s ! AF (extCase c) okhotnik.anim (gennum okhotnik.g Pl) ++ 
              okhotnik.s ! NF kazhduj.n (extCase c) nompl  ;
	sgg =>
	  case c of {
	    PF Nom _ _ => 
              kazhduj.s ! AF Nom okhotnik.anim (gennum okhotnik.g kazhduj.n) ++ 
              okhotnik.s ! NF Sg Gen sgg; 
	    _ => 
              kazhduj.s ! AF (extCase c) okhotnik.anim (gennum okhotnik.g kazhduj.n) ++ 
              okhotnik.s ! NF Pl (extCase c) sgg } ;
	plg =>
	  case c of {
	    PF Nom _ _ => 
              kazhduj.s ! AF Nom okhotnik.anim (gennum okhotnik.g kazhduj.n) ++ 
              okhotnik.s ! NF Pl Gen plg ; 
	    _ => 
              kazhduj.s ! AF (extCase c) okhotnik.anim (gennum okhotnik.g kazhduj.n) ++ 
              okhotnik.s ! NF Pl (extCase c) plg }
	  };
      n = kazhduj.n ; 
      p = P3 ;
      pron = False;
      g = case kazhduj.g of { PNoGen => (PGen okhotnik.g); _ => kazhduj.g };
      anim = okhotnik.anim 
    } ;

    UsePN masha = {
      s = \\c => masha.s ! (extCase c) ; 
      p = P3; g = PGen masha.g ; anim = masha.anim ; 
      n = Sg; nComp = Sg; pron = False} ;

    UsePron p = p ** {anim = Inanimate};

    PredetNP pred np = {
      s = \\pf => pred.s! (AF (extCase pf) np.anim (gennum (pgen2gen np.g) np.n))++ np.s ! pf ;
      n = np.n;
      p = np.p;
      g = np.g;
      anim = np.anim;
      pron = np.pron
      } ;

    PPartNP np v2 = {
      s = \\pf => np.s ! pf ++ v2.s ! VFORM Act VINF ; 
      -- no participles in the Verbum type as they behave as adjectives
      n = np.n;
      p = np.p;
      g = np.g;
      anim = np.anim;
      pron = np.pron
      } ;

    AdvNP np adv = {
      s = \\pf => np.s ! pf ++ adv.s ;
      n = np.n;
      p = np.p;
      g = np.g;
      anim = np.anim;
      pron = np.pron
      } ;

-- 1.4 additions AR 17/6/2008

    DetNP kazhduj = 
     let
       g = Neut ; ----
       anim = Inanimate ;
     in {
      s = \\c => kazhduj.s ! AF (extCase c) anim (gennum g kazhduj.n) ;
      n = kazhduj.n ; 
      p = P3 ;
      pron = False;
      g = case kazhduj.g of { PNoGen => (PGen g); _ => kazhduj.g };
      anim = anim 
    } ;

    DetQuantOrd quant num ord = {
      s =  \\af => quant.s !af ++ num.s ! (genAF af) ! (animAF af) ! (caseAF af)  ++ ord.s!af ; 
      n = num.n ;
      g = quant.g;
      c = quant.c;
      size = quant.size
      } ;

    DetQuant quant num = {
      s =  \\af => quant.s !af ++ num.s ! (genAF af) ! (animAF af) ! (caseAF af);
      n = num.n ;
      g = quant.g;
      c = quant.c;
      size = num.size
      } ;
{-
    DetArtOrd quant num ord = {
      s =  \\af => quant.s !af ++ num.s! (caseAF af) ! (genAF af)  ++ ord.s!af ; 
      n = num.n ;
      g = quant.g;
      c = quant.c
      } ;

    DetArtCard quant num = {
      s =  \\af => quant.s !af ++ num.s! (caseAF af) ! (genAF af) ;
      n = num.n ;
      g = quant.g;
      c = quant.c
      } ;
-}
--    MassDet = {s = \\_=>[] ; c=Nom; g = PNoGen; n = Sg} ;

    MassNP okhotnik = {
      s = \\c => okhotnik.s ! NF Sg (extCase c) nom ; 
      n = Sg ; 
      p = P3 ;
      pron = False;
      g = PGen okhotnik.g ;
      anim = okhotnik.anim 
    } ;
{-
    DetArtSg kazhduj okhotnik = {
      s = \\c =>  -- art case always Nom (AR 17/6/2008) 
          kazhduj.s ! AF (extCase c) okhotnik.anim (gennum okhotnik.g Sg) ++ 
          okhotnik.s ! Sg ! (extCase c) ; 
      n = Sg ; 
      p = P3 ;
      pron = False;
      g = case kazhduj.g of { PNoGen => (PGen okhotnik.g); _ => kazhduj.g };
      anim = okhotnik.anim 
    } ;

    DetArtPl kazhduj okhotnik = {
      s = \\c =>  -- art case always Nom (AR 17/6/2008) 
          kazhduj.s ! AF (extCase c) okhotnik.anim (gennum okhotnik.g Pl) ++ 
          okhotnik.s ! Pl ! (extCase c) ; 
      n = Pl ; 
      p = P3 ;
      pron = False;
      g = case kazhduj.g of { PNoGen => (PGen okhotnik.g); _ => kazhduj.g };
      anim = okhotnik.anim 
    } ;
-}
    PossPron p = {s = \\af => p.s ! mkPronForm (caseAF af) No (Poss (gennum (genAF af) (numAF af) )); c=Nom; g = PNoGen; size = nom} ;

   NumCard c = c ;
   NumSg = {s = \\_,_,_ => [] ; n = Sg ; size = nom} ;
   NumPl = {s = \\_,_,_ => [] ; n = Pl ; size = nompl} ;

   OrdNumeral numeral = variants {} ; ---- TODO; needed to compile Constructors
   OrdDigits numeral = variants {} ; ---- TODO; needed to compile Constructors
----   OrdDigits TODO
 --  {s = \\ af => (uy_j_EndDecl (numeral.s ! caseAF af ! genAF af)).s!af} ;

    NumNumeral n = n ;
    NumDigits n = {s = \\_,_,_ => n.s ; n = n.n ; size = n.size } ;

    AdNum adn num = {s = \\c,a,n => adn.s ++ num.s!c!a!n ; n = num.n ; size = num.size} ;

    OrdSuperl a = {s = a.s!Posit};

    DefArt = {s = \\_=>[] ; c=Nom; g = PNoGen; size = nom };
    IndefArt = { s = \\_=>[] ; c=Nom; g = PNoGen; size = nom };

  UseN noun = noun ;
  UseN2 noun = noun ;

-- The application of a function gives, in the first place, a common noun:
-- "ключ от дома". From this, other rules of the resource grammar 
-- give noun phrases, such as "ключи от дома", "ключи от дома
-- и от машины", and "ключ от дома и машины" (the
-- latter two corresponding to distributive and collective functions,
-- respectively). Semantics will eventually tell when each
-- of the readings is meaningful.

  ComplN2 f x = {
    s = \\nf => case x.pron of {
                  True => x.s ! (case nf of {NF n c size => mkPronForm c No (Poss (gennum f.g n))}) ++ f.s ! nf ;
                  False => f.s ! nf ++ f.c2.s ++ 
                           x.s ! (case nf of {NF n c size => mkPronForm f.c2.c Yes (Poss (gennum f.g n))})
                };
    g = f.g ;
    anim = f.anim
    } ;

-- Two-place functions add one argument place.
-- There application starts by filling the first place.

  ComplN3 f x = {
    s  = \\nf => f.s ! nf ++ f.c2.s ++ x.s ! (PF f.c2.c Yes NonPoss) ;
    g  = f.g ;
    anim = f.anim ;
    c2 = f.c3 ;
    } ;

  ---- AR 17/12/2008
  Use2N3 f = {
      s = f.s ;
      g = f.g ; 
      anim = f.anim ;
      c2 = f.c2
      } ;

  ---- AR 17/12/2008
  Use3N3 f = {
      s = f.s ;
      g = f.g ; 
      anim = f.anim ;
      c2 = f.c3
      } ;


-- The two main functions of adjective are in predication ("Иван - молод")
-- and in modification ("молодой человек"). Predication will be defined
-- later, in the chapter on verbs.

  AdjCN ap cn = {
    s = \\nf => ap.s ! case nf of {NF Sg Gen sgg => AF Nom cn.anim GPl ;
                                   NF n c size => AF c cn.anim (gennum cn.g n)} ++ 
                cn.s ! nf ;
    g = cn.g ;
    anim = cn.anim
    } ;

-- This is a source of the "man with a telescope" ambiguity, and may produce
-- strange things, like "машины всегда".
-- Semantics will have to make finer distinctions among adverbials.

  AdvCN cn adv = {
    s = \\nf => cn.s ! nf ++ adv.s ;
    g = cn.g ;
    anim = cn.anim 
    } ;

-- Constructions like "the idea that two is even" are formed at the
-- first place as common nouns, so that one can also have "a suggestion that...".

  SentCN idea x = {
    s = \\nf => idea.s ! nf ++ x.s ; 
    g = idea.g ;
    anim = idea.anim
    } ;

  RelCN idea x = {
    s = \\nf => idea.s ! nf ++ case nf of {NF n c size => x.s ! (gennum idea.g n)!c!idea.anim} ; 
    g = idea.g ;
    anim = idea.anim
    } ;

  ---- AR 17/12/2008
  ApposCN cn s = {
    s = \\nf => cn.s ! nf ++ s.s ! (case nf of {NF n c size => PF c No NonPoss}) ; 
    g = cn.g ;
    anim = cn.anim
    } ;

  RelNP np rel = {
      s = \\c => np.s ! c ++ rel.s ! (gennum (pgen2gen np.g) np.n) ! extCase c ! np.anim ; 
      n = np.n ;
      p = np.p ;
      pron = np.pron ;
      g = np.g ;
      anim = np.anim ;
      nComp = np.nComp
      } ;


}

