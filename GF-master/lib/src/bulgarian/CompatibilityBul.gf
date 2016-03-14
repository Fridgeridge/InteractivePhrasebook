--# -path=.:../abstract:../common

concrete CompatibilityBul of Compatibility = CatBul ** open Prelude, ResBul in {

-- from Noun 19/4/2008
flags
  coding = cp1251 ;

lin
  NumInt n = {s = \\_ => n.s; n = Pl; nonEmpty = True} ;
  OrdInt n = {s = \\aform => n.s ++ "-" ++ 
                             case aform of {
                               ASg Masc Indef => "��" ;
                               ASg Fem  Indef => "��" ;
                               ASg Neut Indef => "��" ;
                               ASg Masc Def   => "���" ;
                               ASg Fem  Def   => "����" ;
                               ASg Neut Def   => "����" ;
                               ASgMascDefNom  => "����" ;
                               APl Indef      => "��" ;
                               APl Def        => "����"
                             }
                } ;

}
