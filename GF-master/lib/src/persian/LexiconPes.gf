--# -path=.:prelude:alltenses

concrete LexiconPes of Lexicon = CatPes ** 
--open ResPnb, Prelude in {
  open ParadigmsPes,MorphoPes, Prelude in {

  flags 
    optimize=values ;
    coding = utf8;

  lin

  airplane_N = mkN01 "هواپیما" inanimate ;
  answer_V2S = mkV2  (compoundV "جواب" (mkV "دادن" "ده")) "به" False; 
  apartment_N = mkN01 "آپارتمان" inanimate;
  apple_N = mkN01 "سیب" inanimate;
  art_N = mkN01 "هنر" inanimate;
  ask_V2Q = mkV2 (mkV_1 "پرسیدن") "از" False;
  baby_N = mkN01 "بچه" animate; -- has variant "کودک"
  bad_A = mkA "بد" ;
  bank_N = mkN01 "بانک" inanimate;
  beautiful_A = mkA "زیبا" ;
  become_VA = mkV "شدن" "شو";
  beer_N = mkN01 "آبجو" inanimate;
  beg_V2V =  mkV2V (compoundV "خواهش" (mkV "کردن" "کن")) "از" "" False;
  big_A = mkA "بزرگ" ;
  bike_N = mkN01 "دوچرخه" inanimate;
  bird_N = mkN02 "پرنده" animate;
  black_A =  mkA "سیاه" ;
  blue_A = mkA "آبی" ;
  boat_N = mkN01 "قایق" inanimate;
  book_N = mkN01 "کتاب" inanimate;
  boot_N = mkN01 "چکمه" inanimate; -- has variant "پوتین"
  boss_N = mkN02 "کارفرما" animate;
  boy_N = mkN02 "پسر" animate;
  bread_N = mkN01 "نان" inanimate;
  break_V2 = mkV2 (mkV "شکستن" "شکن") "را";
  broad_A = mkA "وسیع" ;
  brother_N2 = (mkN01 "برادر" animate) ** {c=""}; 
  brown_A = mkA ["قهوه ای"] ; 
  butter_N = mkN01 "کره" inanimate;
  buy_V2 = mkV2 (mkV_1 "خریدن") "را";
  camera_N = mkN01 "دوربین" inanimate;
  cap_N = mkCmpdNoun1 "کلاه" (mkN01 "کپ" animate); 
  car_N = mkN01 "ماشین" inanimate; -- has variant "اتومبیل"
  carpet_N = mkN01 "فرش" inanimate;
  cat_N = mkN01 "گربه" animate;
  ceiling_N = mkN01 "سقف" inanimate;
  chair_N = mkN01 "صندلی" inanimate;
  cheese_N = mkN01 "پنیر" inanimate;
  child_N = mkN02 "فرزند"  animate; -- has variant "بچه"
  church_N = mkN01 "کلیسا"  inanimate;
  city_N = mkN01 "شهر" inanimate;
  clean_A = mkA "تمیز" ;
  clever_A = mkA "باهوش" ["با هوشمندی"];  
  close_V2 =  mkV2 (mkV "بستن" "بند") "را"; 
  coat_N = mkN01 "کت" inanimate;
  cold_A = mkA "سرد" ;
  come_V = mkV "آمدن" "آی" ;
  computer_N = mkN01 "کامپیوتر" inanimate; -- also vaiant "رایانه"
  country_N = mkN01 "کشور" inanimate;
  
-- Note: cousin inflects for gender and for being a mother's or a father's relatives in persian
-- The following is an example which is the daughter of your mom's brother
  cousin_N = mkCmpdNoun1 "دختر" (mkN01 "دایی" animate);  
  cow_N = mkN01 "گاو" animate;
  die_V = mkV "مردن" "میر" ;
  dirty_A = mkA "کثیف" ;
  distance_N3 = (mkN "فاصله" "فواصل" inanimate ) ** {c2="از" ; c3 = "تا"};
  doctor_N = mkN01 "دکتر" animate; -- has variant "پزشک", but only a doctor in medicine 
  dog_N = mkN01 "سگ" animate;
  door_N = mkN01 "در" inanimate;
  drink_V2 = mkV2 (mkV_1 "نوشیدن") "را";
--  easy_A2V = mkA "آسان" "" ;
  eat_V2 = mkV2 (mkV_2 "خوردن") "را" ;
  empty_A = mkA "خالی" ;
  enemy_N = mkN02 "دشمن" animate;
  factory_N = mkN01 "کارخانه" inanimate;
  father_N2 = (mkN02 "پدر" animate) ** {c=""};
  fear_VS = mkV_1 "ترسیدن"; 
  find_V2 = mkV2 (compoundV "پیدا" (mkV "کردن" "کن") ) "را";  
  fish_N = mkN01 "ماهی" animate;
  floor_N = mkN01 "زمین" inanimate; -- Note: floor in persian can have 3 different translations
  forget_V2 = mkV2 (compoundV "فراموش" (mkV "کردن" "کن")) "را" ; 
  fridge_N = mkN01 "یخچال" inanimate;
  friend_N = mkN02 "دوست" animate;
  fruit_N = mkN01 "میوه" inanimate;
--  fun_AV = mkAV  "جالب" ;
  garden_N = mkN01 "باغ" inanimate;
  girl_N = mkN02 "دختر" animate;
  glove_N = mkN01 "دستکش" inanimate;
  gold_N = mkN01 "طلا" inanimate;
  good_A = mkA "خوب" ;
  go_V = mkV "رفتن" "رو";
  green_A = mkA "سبز" ;
  harbour_N = mkN "بندر" "بنادر" inanimate;
--  hate_V2 = mkV2 (compoundV "متنفر" (mkToBe "بودن" "باش" "هست")) "از" False; -- needs from/ verb to be
  hat_N = mkN01 "کلاه" inanimate;
  have_V2 = mkV2 haveVerb "را" ; 
  hear_V2 = mkV2 (mkV "شنیدن" "شنو") "را" ;
  hill_N = mkN01 "تپه" inanimate;
--  hope_VS = compoundV "امیدوار" (mkToBe "بودن" "باش" "هست");
  horse_N = mkN01 "اسب" animate;
  hot_A = mkA "داغ" ["داغ داغ"] ;
  house_N = mkN01 "خانه" inanimate;
  important_A = mkA "مهم" ["با اهمیت"];
  industry_N = mkN "صنعت" "صنایع" inanimate;
  iron_N = mkN01 "آهن" inanimate;
  king_N = mkN "پادشاه" "پادشاهان" animate;
  know_V2 = mkV2 (mkV "شناختن" "شناس") "را";
  know_VS = (mkV_1 "دانستن");
  know_VQ = (mkV_1 "دانستن") ;
  lake_N = mkN01 "دریاچه" inanimate;
  lamp_N = mkN01 "چراغ" inanimate; -- also "لامپ", but they have different usage
  learn_V2 = mkV2 (compoundV "یاد"(mkV "گرفتن" "گیر")) "را";
  leather_N = mkN01 "چرم" inanimate; -- is uncountable 
  leave_V2 = mkV2 (compoundV "ترک"(mkV "کردن" "کن")) "را";
  like_V2 = mkV2 (compoundV "دوست" haveVerb) "را"; 
  listen_V2 = mkV2 (compoundV "گوش" (mkV "دادن" "ده")) "به" False; -- has a diferent preposition :"به"
  live_V = compoundV "زندگی" (mkV "کردن" "کن");
  long_A = mkA "بلند" ;
  lose_V2 = mkV2 (compoundV "گم" (mkV "کردن" "کن")) "را" ; 
  love_N = mkN01 "عشق" inanimate; 
  love_V2 = mkV2 (compoundV "دوست" haveVerb) "را"; -- also possible: love_V2 = mkV2 (compoundV "عاشق" (mkToBe "بودن" "باش" "هست"));
  man_N = mkN02 "مرد" animate; 
  married_A2 = mkA "متأهل" "";
  meat_N = mkN01 "گوشت" inanimate;
  milk_N = mkN01 "شیر" inanimate;
  moon_N = mkN01 "ماه" inanimate; -- is this not a proper noun?
  mother_N2 = (mkN02 "مادر" animate) ** {c=""};   
  mountain_N = mkN01 "کوه" inanimate;
  music_N = mkN "موسیقی" "موسیقی" animate; 
  narrow_A = mkA "باریک" ;
  new_A = mkA "نو" "تازه";
  newspaper_N = mkN01 "روزنامه" inanimate;
  oil_N = mkN "نفت" "نفت" inanimate; -- also "روغن"
  old_A = mkA "پیر" "پیرانه";
  open_V2 = mkV2 (compoundV "باز" (mkV "کردن" "کن")) "را";
  paint_V2A = mkV2 (compoundV "رنگ" (mkV "کردن" "کن")) "را" ;
  paper_N = mkN01 "کاغذ" inanimate;
  paris_PN = mkPN "پاریس" inanimate;  
  peace_N = mkN01 "صلح" inanimate; -- also "آرامش"  
  pen_N = mkN01 "قلم" inanimate; -- has  variant "خودکار"
  planet_N = mkN01 "سیّاره" inanimate;
  plastic_N = mkN01 "پلاستیک" inanimate; -- is uncountable 
  play_V2 = mkV2 (mkV "نواختن" "نواز") "را" ; 
  policeman_N = mkCmpdNoun2 (mkN02 "مأمور" animate) "پلیس"; 
  priest_N = mkN01 "کشیش" animate;
--  probable_AS = mkAS (regA "محتمل") ;
  queen_N = mkN01 "ملکه" animate;
  radio_N = mkN01 "رادیو" inanimate;
  rain_V0 = compoundV "باران" (mkV "آمدن" "آی" ) ;
  read_V2 = mkV2 (mkV_2 "خواندن") "را";
  red_A = mkA "قرمز" ;
  religion_N = mkN "مذهب" "مذاهب" inanimate;
  restaurant_N = mkN01 "رستوران" inanimate;
  river_N = mkN01 "رودخانه" inanimate;
  rock_N = mkN01 "صخره" inanimate;
  roof_N = mkN01 "بام" inanimate; -- has  variant "سقف"
  rubber_N = mkN01 "پاککن" inanimate; -- also "لاستیک" 
  run_V = mkV_1 "دویدن" ;
  say_VS = mkV "گفتن" "گوی" ;
  school_N = mkN "مدرسه" "مدارس" inanimate;
  science_N = mkN "علم" "علوم" inanimate; -- also "دانش"
  sea_N = mkN01 "دریا" inanimate;
  seek_V2 = mkV2 (compoundV "جستجو" (mkV "کردن" "کن")) "را";
  see_V2 = mkV2 (mkV "دیدن" "بین") "را" ;
  sell_V3 = mkV3 (mkV "فروختن" "فروش") "را" "به"; 
  send_V3 = mkV3 (mkV_1 "فرستادن") "را" "برای"; 
  sheep_N = mkN01 "گوسفند" animate;
  ship_N = mkN01 "کشتی" inanimate;
  shirt_N = mkN01 "پیراهن" inanimate;
  shoe_N = mkN01 "کفش" inanimate;
  shop_N = mkN01 "فروشگاه" inanimate; -- has variant "مغازه"
  short_A = mkA "کوتاه" ;
  silver_N = mkN "نقره" ["نقره جات"] inanimate; -- add new function which applies + "جات"
  sister_N = mkN02 "خواهر" animate;
  sleep_V = mkV_1 "خوابیدن" ;
  small_A = mkA "کوچک" ;
  snake_N = mkN01 "مار" animate;
  sock_N = mkN01 "جوراب" inanimate;
  speak_V2 = mkV2 (compoundV "صحبت" (mkV "کردن" "کن")) "" False; 
  star_N = mkN01 "ستاره" animate;
  steel_N = mkN01 "فولاد" inanimate; -- also "استیل"
  stone_N = mkN01 "سنگ" inanimate;
  stove_N = mkN01 "اجاق" inanimate;
  student_N = mkCmpdNoun1 "دانش" (mkN02 "آموز" animate); -- also "دانشجو"
  stupid_A = mkA "ابله" "ابلهانه" ;
  sun_N = mkN01 "خورشید" inanimate; -- is this not a proper noun?!!!
  switch8off_V2 = mkV2 (compoundV "خاموش" (mkV "کردن" "کن")) "را";
  switch8on_V2 = mkV2 (compoundV "روشن" (mkV "کردن" "کن")) "را";
  table_N = mkN01 "میز" inanimate;
  talk_V3 = mkV3 (compoundV "حرف" (mkV "زدن" "زن")) "با" [" درباره ی"];
  teacher_N = mkN02 "معلم" animate;
  teach_V2 = mkV2 (compoundV "آموزش" (mkV "دادن" "ده")) "را";
  television_N = mkN01 "تلوزیون" inanimate;
  thick_A = mkA "کلفت" ;
  thin_A = mkA "نازک" ;
  train_N = mkN01 "قطار" inanimate;
  travel_V = compoundV "سفر" (mkV "کردن" "کن");
  tree_N = mkN02 "درخت" animate;
  trousers_N = mkN01 "شلوار" inanimate;
  ugly_A = mkA "زشت" ;
  understand_V2 = mkV2 (mkV_1 "فهمیدن") "را";
  university_N = mkN01 "دانشگاه" inanimate;
  village_N = mkN01 "روستا" inanimate;
--  wait_V2 = mkV2 (compoundV "منتظر" (mkVToBe "بودن" "باش")); 
  walk_V = compoundV  "راه"  (mkV "رفتن" "رو");
  warm_A = mkA "گرم" ;
  war_N = mkN01 "جنگ" inanimate;
--  watch_V2 = mkV2 (compoundV "مراقب" (mkVToBe "بودن" "باش")); -- check harfe rabt!!!
  water_N = mkN01 "آب" inanimate;
  white_A = mkA "سفید" ;
  window_N = mkN01 "پنجره" inanimate;
  wine_N = mkN01 "شراب" inanimate;
  win_V2 = mkV2 (compoundV "برنده" (mkV "شدن" "شو")) "را"; -- also possible with simple verb: mkV_2 "بردن"  
  woman_N = mkN02 "زن" animate;
--  wonder_VQ = compoundV "متعجب" (mkVToBe "بودن" "باش") ;  
  wood_N = mkN01 "چوب" inanimate;
  write_V2 = mkV2 (mkV "نوشتن" "نویس") "را" ;
  yellow_A = mkA "زرد" ;
  young_A = mkA "جوان""جوانانه" ;
  do_V2 = mkV2 (compoundV "انجام" (mkV "دادن" "ده")) "را";
  now_Adv = ss "حالا" ;
  already_Adv = ss "قبلاً" ;
  song_N = mkN01 "آواز" inanimate;
  add_V3 = mkV3 (compoundV "اضافه" (mkV "کردن" "کن")) "را" "به" ;
  number_N = mkN01 "عدد" inanimate; -- also "تعداد"
  put_V2 = mkV2 (mkV "گذاشتن" "گذار") "را";
  stop_V = compoundV "توقف" (mkV "کردن" "کن");
  jump_V = mkV_1 "پریدن";
{-
  left_Ord = {s = "چپ" ; n = singular};
  right_Ord = {s= "راست" ; n = singular};
-}  
  far_Adv = ss "دور" ;
  correct_A = mkA "درست" ;
  dry_A = mkA "خشک" ["به خشکی"] ;
  dull_A = mkA ["ملال آور"] ["به طرزی ملال آور"] ; 
  full_A = mkA "پر" ;
  heavy_A = mkA "سنگین" ;
  near_A = mkA "نزدیک" ;
  rotten_A = mkA "خراب" ;
  round_A = mkA "گرد" ;
  sharp_A = mkA "تیز" ;
  smooth_A = mkA "نرم" ;
  straight_A = mkA "مستقیم" "مستقیماً";
  wet_A = mkA "خیس" ; 
  wide_A = mkA "پهن" ;
  animal_N = mkN "حیوان" "حیوانات" animate;
  ashes_N = mkN01 "خاکستر" inanimate; 
  back_N = mkN01 "کمر" inanimate; 
  bark_N = mkN01 "عوعو" inanimate;
  belly_N = mkN01 "شکم" inanimate;
  blood_N = mkN01 "خون" inanimate;
  bone_N = mkN01 "استخوان" inanimate;
  breast_N = mkN01 "سینه" inanimate;
  cloud_N = mkN01 "ابر" inanimate;
  day_N = mkN01 "روز" inanimate;
  dust_N = mkN01 "غبار" inanimate;
  ear_N = mkN01 "گوش" inanimate;
  earth_N = mkN01 "زمین" inanimate; -- also "خاک" 
  egg_N = mkCmpdNoun1 "تخم" (mkN01 "مرغ" inanimate);  
  eye_N = mkN01 "چشم" inanimate ;
  fat_N = mkN01 "چربی" inanimate;
  feather_N = mkN01 "پر" inanimate;
  fingernail_N = mkN01 "ناخن" inanimate;
  fire_N = mkN01 "آتش" inanimate;
  flower_N = mkN01 "گل" inanimate;
  fog_N = mkN01 "مه" inanimate;
  foot_N = mkN01 "پا" inanimate;
  forest_N = mkN01 "جنگل" inanimate;
  grass_N = mkN01 "چمن" inanimate;
  guts_N = mkN01 "شهامت" inanimate;
  hair_N = mkN01 "مو" inanimate;
  hand_N = mkN01 "دست" inanimate;
  head_N = mkN01 "سر" inanimate;
  heart_N = mkN01 "قلب" inanimate;
  horn_N = mkN01 "بوق" inanimate; -- also "شاخ"
  husband_N = mkN02 "شوهر" animate;
  ice_N = mkN01 "یخ" inanimate;
  knee_N = mkN01 "زانو" inanimate;
  leaf_N = mkN01 "برگ" inanimate;
  leg_N = mkN01 "پا" inanimate;
  liver_N = mkN01 "رودخانه" inanimate;
  louse_N = mkN01 "شپش" inanimate;
  mouth_N = mkN01 "دهان" inanimate;
  name_N = mkN01 "نام" inanimate; -- has variant "اسم"
  neck_N = mkN01 "گردن" inanimate;
  night_N = mkN01 "شب" inanimate;
  nose_N = mkN01 "بینی" inanimate;
  person_N = mkN "شخص" "اشخاص" animate;
  rain_N = mkN01 "باران" inanimate; 
  road_N = mkN01 "جاده" inanimate;
  root_N = mkN01 "ریشه" inanimate;
  rope_N = mkN01 "طناب" inanimate;
  salt_N = mkN01 "نمک" inanimate;
  sand_N = mkN01 "ماسه" inanimate;
  seed_N = mkN01 "دانه"  inanimate;
  skin_N = mkN01 "پوست" inanimate;
  sky_N = mkN01 "آسمان" inanimate;
  smoke_N = mkN01 "دود" inanimate;
  snow_N = mkN01 "برف" inanimate;
  stick_N = mkN01 "ترکه" inanimate;
  tail_N = mkN01 "دم" inanimate;
  tongue_N = mkN01 "زبان" inanimate;
  tooth_N = mkN01 "دندان" inanimate;
  wife_N = mkN02 "همسر" animate;
  wind_N = mkN01 "باد" inanimate;
  wing_N = mkN01 "بال" inanimate;
  worm_N = mkN01 "کرم" inanimate;
  year_N = mkN01 "سال" inanimate;
  blow_V = mkV_1 "دمیدن" ;
  breathe_V = compoundV "نفس" (mkV_1 "کشیدن");
  burn_V = mkV "سوختن" "سوز" ;
  dig_V = mkV_2 "کندن" ;
  fall_V = mkV_1 "افتادن" ;
--  float_V = compoundV "شناور" (mkToBe "بودن" "باش" "هست") ;
  flow_V = compoundV "جاری" (mkV "شدن" "شو") ;
  fly_V = compoundV "پرواز" (mkV "کردن" "کن") ;
  freeze_V = compoundV "یخ" (mkV "زدن" "زن") ;
  give_V3 = mkV3 (mkV "دادن" "ده") "را" "به";
  laugh_V = mkV_1 "خندیدن"  ;
  lie_N = mkN01 "دروغ" inanimate;
  lie_V = compoundV "دروغ" (mkV "گفتن" "گو" );
  play_V = compoundV "بازی" (mkV "کردن" "کن");
  sew_V = mkV "دوختن" "دوز" ;
  sing_V = compoundV "آواز" (mkV_2 "خواندن");
  sit_V = mkV "نشستن" "نشین" ;
  smell_V = compoundV "بو" (mkV "دادن" "ده");
  spit_V = compoundV "تف" (mkV "کردن" "کن");
  stand_V = mkV_1 "ایستادن";
  swell_V = compoundV "ورم" (mkV "کردن" "کن");
  swim_V = compoundV "شنا" (mkV "کردن" "کن");
  think_V = compoundV "فکر" (mkV "کردن" "کن");
  turn_V = mkV_1 "چرخیدن" ;
  vomit_V = compoundV "استفراغ" (mkV "کردن" "کن");
  bite_V2 = mkV2 (compoundV "گاز" (mkV "گرفتن" "گیر")) "را";
  count_V2 = mkV2 (mkV_2 "شماردن") "را";
  cut_V2 = mkV2 (mkV_1 "بریدن") ;
  fear_V2 = mkV2 (mkV_1 "ترسیدن") "از";
  fight_V2 = mkV2 (mkV_1 "جنگیدن") "با" False;
  hit_V2 = mkV2 (compoundV "ضربه" (mkV "زدن" "زن")) "به" False;
  hold_V2 = mkV2 (compoundV "نگه" haveVerb) "را";
  hunt_V2 = mkV2 (compoundV "شکار" (mkV "کردن" "کن")) "را";
  kill_V2 = mkV2 ( mkV_2 "کشتن") "را";
  pull_V2 = mkV2 (mkV_1 "کشیدن") "را";
  push_V2 = mkV2 (compoundV "هل" (mkV "دادن" "ده")) "را" ;
  rub_V2 = mkV2 (mkV_1 "مالیدن") "را";
  scratch_V2 = mkV2 (mkV_1 "خراشیدن") "را" ;
  split_V2 = mkV2 (compoundV "تقسیم" (mkV "کردن" "کن")) "را";
  squeeze_V2 = mkV2 (compoundV "له" (mkV "کردن" "کن")) "را";
  stab_V2 = mkV2 (compoundV "چاقو" (mkV "زدن" "زن")) "به" False; 
  suck_V2 = mkV2 (mkV_1 "مکیدن") "را" ;
  throw_V2 = mkV2 (compoundV "پرتاب" (mkV "کردن" "کن")) "را";
  tie_V2 = mkV2 (compoundV "گره" (mkV "زدن" "زن")) "را";
  wash_V2 = mkV2 (mkV "شستن" "شور") "را" ; -- also "شوی" which is the very formal form of the present root
  wipe_V2 = mkV2 (compoundV "پاک" (mkV "کردن" "کن")) "را";

----  other_A = regA "دیگر" ;

  grammar_N = mkCmpdNoun1 "دستور" (mkN01 "زبان" inanimate);  
  language_N = mkN01 "زبان" inanimate;
  rule_N = mkN "قانون" "قوانین" inanimate;

---- added 4/6/2007
    john_PN = mkPN "جان" inanimate; 
    question_N = mkN01 "سؤال" inanimate; -- has variant "پرسش"
    ready_A = mkA "آماده" ["با آمادگی"] ;
    reason_N = mkN "دلیل" "دلایل" inanimate;
    today_Adv = ss "امروز" ;
    uncertain_A = mkA "نامعلوم" ["با تردید"];
     
}
