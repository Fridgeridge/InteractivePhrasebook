concrete QueryFin of Query = {
  lincat 
    Answer, Question, Object = Str ;
  lin 
    Even  = pred "parillinen" ;
    Odd   = pred "pariton" ;
    Prime = pred "alkuluku" ;
    Number i = i.s ;
    Yes = "kyll�" ;
    No = "ei" ;
  oper
    pred : Str -> Str -> Str = \f,x -> "onko" ++ x ++ f ;
}
