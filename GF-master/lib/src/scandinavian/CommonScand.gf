--1 Auxiliary operations common for Scandinavian languages.
--
-- This module contains operations that are shared by the Scandinavian
-- languages, without dependence on parameters.

resource CommonScand = ParamX ** open Prelude in {

  flags optimize=all ;
    coding=utf8 ;

param
  Species = Indef | Def ;
  Case    = Nom | Gen ;
  Voice   = Act | Pass ;

-- The principal word orders in predication: main clause, inverted, subordinate.

  Order   = Main | Inv | Sub ;

-- The types of noun definiteness required by determiners. Examples:
-- "ett stort hus" (DIndef), "mitt stora hus" (DDef Indef), 
-- "det stora huset" (DDed Def).

  DetSpecies = DIndef | DDef Species ;

-- These are the gender-number combinations needed for adjective inflection,
-- minimizing the number of forms in the lexicon: there is no gender dependency
-- in the plural, and only two genders in the singular even in Norwegian.

  GenNum  = GSg Gender | GPl ;

  Gender  = Utr | Neutr ;

  AForm   = AF AFormGrad Case | AAdv ;

  AFormGrad =
     APosit  AFormPos
   | ACompar  
   | ASuperl AFormSup ;

-- The $Number$ in $Weak$ only matters in "lilla"/"små".

  AFormPos = Strong GenNum | Weak Number ;
  AFormSup = SupStrong | SupWeak ;

  VForm = 
     VF VFin
   | VI VInf ;

  VFin =
     VPres Voice
   | VPret Voice   --# notpresent
   | VImper Voice
   ;

  VInf = 
     VInfin  Voice
   | VSupin  Voice  --# notpresent
   | VPtPret AFormPos Case
   | VPtPres Number Species Case
   ;

  VPForm = 
     VPFinite STense Anteriority
   | VPImperat
   | VPInfinit Anteriority
   ;

  PartForm =
     PartPret AFormPos Case
   | PartPres Number Species Case
   ;

  VType = VAct | VPass | VRefl ;

  NPForm = NPNom | NPAcc | NPPoss GenNum Case ;

  RCase = RNom | RGen | RPrep Bool ;

  RAgr = RNoAg | RAg Gender Number Person ;

  PredetAgr = PNoAg | PAg Number ;

   STense =
     SPres 
   | SPast   --# notpresent
   | SFut    --# notpresent
   | SFutKommer   --# notpresent -- komma att
   | SCond   --# notpresent
   ;




oper
  Complement : Type = {s : Str ; hasPrep : Bool} ;

  Agr : PType = {g : Gender ; n : Number ; p : Person} ;

  nominative : NPForm = NPNom ;
  accusative : NPForm = NPAcc ;

  caseNP : NPForm -> Case = \np -> case np of {
    NPPoss _ _ => Gen ;
    _ => Nom
    } ;

  specDet : DetSpecies -> Species = \d -> case d of {
    DDef Def => Def ;
    _ => Indef
    } ;

  mkComplement : Str -> Complement = \s -> {
    s = s ;
    hasPrep = case s of {
      "" => False ;
      _ => True
      }
    } ;

-- Used in $Noun.AdjCN$.

  agrAdjNP : Agr -> DetSpecies -> AFormPos = \a ->
    agrAdj (gennumAgr a) ;

  agrAdj : GenNum -> DetSpecies -> AFormPos = \gn,d -> 
    case <<gn,d> : GenNum * DetSpecies> of {
      <_,  DIndef> => Strong gn ;
      <GPl,DDef _> => Weak Pl ;
      _            => Weak Sg
    } ;

  gennum : Gender -> Number -> GenNum = \g,n ->
      case n of {
        Sg => GSg g ;
        Pl => GPl
        } ;

  gennumAgr : Agr -> GenNum = \a -> gennum a.g a.n ;

  aformpos2agr : AFormPos -> Agr = \af -> case af of {
    Strong (GSg g) => {p = P3 ; n = Sg ; g = g} ;
    Strong GPl => {p = P3 ; n = Pl ; g = Utr} ; -- loss of gender does not matter in Pl
    Weak n => {p = P3 ; n = n ; g = Utr} ---- loss of gender: *det stor blivande huset
    } ;


-- Used in $DiffScand.predV$.

  vFin : STense -> Voice -> VForm = \t,v -> case t of {
    SPres => VF (VPres v) 
      ; --# notpresent
    SPast => VF (VPret v) ;  --# notpresent
    _ => VI (VInfin v) --# notpresent
    } ;
    
-- Used in $ConjunctionScand$.

  conjGender : Gender -> Gender -> Gender = \g,h -> case g of {
    Utr => h ;
    _ => Neutr 
    } ;

  conjAgr : (_,_ : Agr) -> Agr = \a,b -> {
    g = conjGender a.g b.g ;
    n = conjNumber a.n b.n ;
    p = conjPerson a.p b.p
    } ;

---

-- For $Lex$.

-- For each lexical category, here are the worst-case constructors.
--
-- But $mkNoun$ is fully defined only for each language, since
-- $Gender$ varies.

  nounForms : (x1,_,_,x4 : Str) -> (Number => Species => Case => Str) = 
      \man,mannen,men,mennen -> \\n,d,c => case <n,d> of {
        <Sg,Indef> => mkCase c man ;
        <Sg,Def>   => mkCase c mannen ;
        <Pl,Indef> => mkCase c men ;
        <Pl,Def>   => mkCase c mennen
        } ;

  Adjective : Type = {s : AForm => Str} ;

  mkAdjective : (x1,_,_,_,_,_,x7 : Str) -> {s : AForm => Str} = 
    \liten, litet, lilla, sma, mindre, minst, minsta -> {
    s = table {
      AF (APosit a) c          => mkCase c (mkAdjPos a liten litet lilla sma) ;
      AF ACompar c             => mkCase c mindre ;
      AF (ASuperl SupStrong) c => mkCase c minst ;
      AF (ASuperl SupWeak) c   => mkCase c minsta ;
      AAdv                     => litet
      }
    } ;

  mkVerb9 : (x1,_,_,_,_,_,_,_,x9 : Str) -> {s : VForm => Str ; vtype : VType} = 
   \finna,finner,finn,fann,funnit,funnen,funnet,funna, finnande -> {
   s = table {
    VF (VPres Act)  => finner ;
    VF (VPres Pass) => mkVoice Pass finn ;
    VF (VPret v)    => mkVoice v fann ;  --# notpresent
    VF (VImper v)   => mkVoice v finn ;
    VI (VInfin v)   => mkVoice v finna ;
    VI (VSupin v)   => mkVoice v funnit ;  --# notpresent
    VI (VPtPret a c)=> mkCase c (mkAdjPos a funnen funnet funna funna) ;
    VI (VPtPres n d c)  => case <n,d> of {
      <Sg,Indef> => mkCase c finnande ;
      <Sg,Def>   => mkCase c (finnande + "t") ;
      <Pl,Indef> => mkCase c (finnande + "n") ;
      <Pl,Def>   => mkCase c (finnande + "na")   ---- TODO "ne" in Dan, Nor
      }
    } ;
   vtype = VAct
   } ;

-- These are useful auxiliaries.

  mkCase : Case -> Str -> Str = \c,f -> case c of {
      Nom => f ;
      Gen => f + case last f of {
        "s" | "z" | "x" => [] ;
        _ => "s"
        }
      } ;

  mkAdjPos : AFormPos -> (s1,_,_,s4 : Str) -> Str =
    \a, liten, litet, lilla, sma ->
    case a of {
      Strong gn => case gn of {
        GSg Utr => liten ;
        GSg Neutr => litet ;
        GPl => sma
      } ;
     Weak Sg => lilla ;
     Weak Pl => sma
   } ;

  mkVoice : Voice -> Str -> Str = \v,s -> case v of {
    Act => s ;
    Pass => s + case last s of {
      "s" => "es" ;
      _   => "s"
      }
    } ;


-- For $Noun$.

  artDef : GenNum -> Str = \gn -> gennumForms "den" "det" "de" ! gn ;

  mkNP : (x1,_,_,_,x5 : Str) -> Gender -> Number -> Person -> 
         {s : NPForm => Str ; a : Agr} = \du,dig,din,ditt,dina,g,n,p -> {
    s = table {
      NPNom => du ;
      NPAcc => dig ;
      NPPoss h c => mkCase c (gennumForms din ditt dina ! h)
      } ;
    a = {
      g = g ;
      n = n ;
      p = p
      }
    } ;

  gennumForms : (x1,x2,x3 : Str) -> GenNum => Str = \den,det,de -> 
    table {
      GSg Utr => den ;
      GSg Neutr => det ;
      _ => de
    } ;  

  detForms : (x1,x2,x3 : Str) -> Gender => Number => Str = \den,det,de -> 
    \\g,n => gennumForms den det de ! gennum g n ;

  regNP : Str -> Str -> Gender -> Number -> {s : NPForm => Str ; a : Agr} =
    \det,dess,g,n ->
    mkNP det det dess dess dess g n P3 ;


-- For $Verb$.

  VP = {
      s : Voice => VPForm => {
        fin : Str ;          -- V1 har  ---s1
        inf : Str            -- V2 sagt ---s4
        } ;
      sp : PartForm => Str ;  -- present or past participle
      a1 : Polarity => Agr => Str ; -- A1 inte ---s3 själv/själva/självt
      n2 : Agr => Str ;      -- N2 dig  ---s5  
      a2 : Str ;             -- A2 idag ---s6
      ext : Str ;            -- S-Ext att hon går   ---s7
      --- ea1,ev2,           --- these depend on params of v and a1
      en2,ea2,eext : Bool    -- indicate if the field exists
      } ;


  insertObj : (Agr => Str) -> VP -> VP = \obj,vp -> vp ** {
    n2 = \\a => obj ! a ++ vp.n2 ! a ;
    en2 = True ;
    } ;

  insertObjPost : (Agr => Str) -> VP -> VP = \obj,vp -> vp ** {
    n2 = \\a => vp.n2 ! a ++ obj ! a ;
    en2 = True ;
    } ;

  insertAdv : Str -> VP -> VP = \adv,vp -> vp ** {
    a2 = vp.a2 ++ adv ;
    ea2 = True ;
    } ;

  insertExt : Str -> VP -> VP = \ext,vp -> vp ** {
    ext = vp.ext ++ ext ;
    eext = True ;
    } ;

  insertAdV : Str -> VP -> VP = \adv -> insertAdVAgr (\\_ => adv) ;

  insertAdVAgr : (Agr => Str) -> VP -> VP = \adv,vp ->vp ** {
    a1 = \\b,a => vp.a1 ! b ! a ++ adv ! a ;
    } ;

  passiveVP : VP -> VP = \vp -> {
    s = \\_ => vp.s ! Pass ; -- forms the s-passive
    sp = vp.sp ;
    a1 = vp.a1 ;
    n2 = vp.n2 ;
    a2 = vp.a2 ;
    ext = vp.ext ;
    en2 = vp.en2 ;
    ea2 = vp.ea2 ;
    eext = vp.eext
    } ;

  infVP : VP -> Agr -> Str = \vp,a -> infVPPlus vp a Simul Pos ;
 
  infVPPlus : VP -> Agr -> Anteriority -> Polarity -> Str = \vp,a,ant,pol -> 
    vp.a1 ! pol ! a ++ (vp.s ! Act ! VPInfinit ant).inf ++ vp.n2 ! a ++ vp.a2 ++ vp.ext ; --- a1

  partVPPlus : VP -> PartForm -> Agr -> Polarity -> Str = \vp,pf,a,pol -> 
    vp.a1 ! pol ! a ++ vp.n2 ! a ++ vp.a2 ++ vp.ext ++ vp.sp ! pf ; -- verb final: i sängen liggande
  partVPPlusPost : VP -> PartForm -> Agr -> Polarity -> Str = \vp,pf,a,pol -> 
    vp.a1 ! pol ! a ++ vp.sp ! pf ++ vp.n2 ! a ++ vp.a2 ++ vp.ext ; -- verb first: liggande i sängen


-- For $Sentence$.

  Clause : Type = {
    s : STense => Anteriority => Polarity => Order => Str
    } ;

  mkClause : Str -> Agr -> VP -> Clause = \subj,agr,vp -> {
      s = \\t,a,b,o => 
        let 
          verb  = vp.s  ! Act ! VPFinite t a ;
          neg   = vp.a1 ! b ! agr ;
          compl = vp.n2 ! agr ++ vp.a2 ++ vp.ext
        in
        case o of {
          Main => subj ++ verb.fin ++ neg ++ verb.inf ++ compl ;
          Inv  => verb.fin ++ subj ++ neg ++ verb.inf ++ compl ;
          Sub  => subj ++ neg ++ verb.fin ++ verb.inf ++ compl
          }
    } ;

}
