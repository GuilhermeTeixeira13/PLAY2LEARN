 -- Insert SUBJECT and QUESTIONS -> PLANETS

INSERT INTO `subjectseng` (`id`, `Name`, `Image`) VALUES (NULL, 'Planets', NULL); 
INSERT INTO `subjectspt` (`id`, `Name`, `Image`) VALUES (NULL, 'Planetas', NULL); 

-- PT QUESTIONS

-- Easy

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Ao movimento da Terra em torno do seu próprio eixo chamamos de ...',1,'movimento de rotação','movimento do dia e da noite','movimento anual', 'movimento de translação');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','O sol é ...',1,'uma estrela','um planeta principal','um planeta secundário', 'um universo');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais pequeno do Sistema Solar?', 1, 'Mercúrio', 'Júpiter', 'Vénus', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o maior planeta do Sistema Solar?', 1, 'Júpiter', 'Saturno', 'Neptuno', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais quente do Sistema Solar?', 1, 'Vénus', 'Mercúrio', 'Terra', 'Júpiter');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'O sexto planeta do Sistema Solar possui um extenso anel, qual é o nome desse planeta?', 1, 'Saturno', 'Júpiter', 'Neptuno', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Qual a duração do movimento de rotação da terra?', 1,'12 horas','365 dias','24 horas', '7 dias');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Ao movimento da Terra em torno do sol chamamos de ...', 1,'movimento de translação','movimento do dia e da noite','movimento anual', 'movimento de rotação');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Qual a duração do movimento de translação da terra?', 1,'365 dias','12 horas','24 horas', '6 meses');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Quantos planetas fazem parte do sistema solar?', 1,'9','8','10', '5');

-- Medium

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'O elemento químico Urânio foi nomeado depois de que planeta?', 2, 'Urânio', 'Saturno', 'Marte', 'Terra');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais distante do sol, no Sistema Solar?', 2, 'Neptuno', 'Urânio', 'Marte', 'Mercúrio');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o segundo planeta mais pequeno, dentro do Sistema Solar?', 2, 'Marte', 'Mercúrio', 'Terra', 'Neptuno');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais próximo em tamanho da Terra?', 2, 'Vénus', 'Mercúrio', 'Marte', 'Saturno');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'A lua Tritão é a maior lua de que planeta?', 2, 'Neptuno', 'Mercúrio', 'Júpiter', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','O movimento de rotação da terra dá origem...', 2,'ao dia e à noite','aos meses do ano','à noite', 'às estacões do ano');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','O movimento de translação da terra da origem ...', 2,'as estações do ano','aos meses do ano','à noite', 'ao dia e à noite');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Identifique um planeta secundário', 2,'Plutão','Neptuno','Júpiter', 'Mercúrio');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Os satélites naturais do sistema solar são astros que giram à volta...', 2,'dos planetas principais','dos planetas secundários','das estrelas', 'do sol');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'As luas da Galiléia orbitam que planeta?', 2, 'Júpiter', 'Mercúrio', 'Terra', 'Neptuno');

-- Hard

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','De manhã, o sol está numa posicão ...', 3,'nascente ou este','nascente ou sul','sul', 'poente ou este');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Ao meio dia, o sol está numa posição...', 3,'sul','nascente ou sul','nascente ou este', 'poente ou este');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Ao anoitecer, o sol está numa posição...', 3,'poente ou este','nascente ou sul','nascente ou este', 'sul');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Qual a representação mais conhecida para indicar os pontos cardiais ?', 3,'Rosa dos ventos','Rosa dos cardeais','Rosa dos pontos cardeais', 'Rosa da orientação');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Escolhe a opcão correta. As estrelas.. ', 3, 'têm luz própria e emitem luz em todas as direções','não têm luz própria e emitem luz em todas as direções','Não têm luz própria e recebem e refletem luz que vem dos planetas', 'têm luz própria e recebem e refletem luz que vem das estrelas');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','Escolhe a opcão correta. Os planetas.. ', 3, 'Não têm luz própria e recebem e refletem luz que vem das estrelas','nao têm luz própria e emitem luz em todas as direções','não têm luz própria e recebem e refletem luz que vem dos planetas', 'têm luz própria e emitem luz em todas as direções');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1','O "Big Bang" foi uma gigantesca explosão que aconteceu ha cerca de 15 mil milhões de anos e deu origem ao..','3','universo','sol','terra', 'sistema solar');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'A lua Titã orbita que planeta?', 3, 'Saturno', 'Mercúrio', 'Marte', 'Júpiter');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta denomidado por "Planeta Vermelho?"', 3, 'Marte', 'Mercúrio', 'Vénus', 'Júpiter');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'As luas da Galiléia orbitam que planeta?', 3, 'Júpiter', 'Mercúrio', 'Terra', 'Neptuno');

-- ENG QUESTIONS

-- Easy

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Earths motion around the sun is called...', 1, 'translation movement', 'day and night movement', 'annual movement', 'rotation movement');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The sun is...', 1, 'a star', 'main planet', 'a secondary planet', 'a universe');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the smallest planet in the Solar System?', 1, 'Mercury', 'Jupiter', 'Venus', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the largest planet in the Solar System?', 1, 'Jupiter', 'Saturn', 'Neptune', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the hottest planet in the Solar System?', 1, 'Venus', 'Mercury', 'Earth', 'Jupiter');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The sixth planet from the Sun features an extensive ring system, what is the name of this planet?', 1, 'Saturn', 'Jupiter', 'Neptune', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'How long does it take for the earth to rotate?', 1, '12 hours', '365 days', '24 hours', '7 days');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The motion of the Earth around its own axis is called...', 1, 'rotation movement', 'day and night movement', 'annual movement', 'translation movement');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the duration of the translation movement of the earth?', 1, '365 days', '12 hours', '24 hours', '6 months');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'How many planets are part of the solar system', 1, '9', '8', '10', '5');

-- Medium

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The chemical element uranium was named after what planet?', 2, 'Uranus', 'Saturn', 'Mars', 'Earth');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What planet in the solar system is farthest from the Sun?', 2, 'Neptune', 'Uranus', 'Mars', 'Mercury');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the second smallest planet in the solar system?', 2, 'Mars', 'Mercury', 'Earth', 'Neptune');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What planet is closest in size to Earth?', 2, 'Venus', 'Mercury', 'Mars', 'Saturn');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Triton is the largest moon of what planet?', 2, 'Neptune', 'Mercury', 'Jupiter', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The earths rotational movement gives rise to...', 2, 'by day and night', 'to the months of the year', 'a night', 'the seasons of the year');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The movement of translation of the earth gives rise to...', 2, 'the seasons of the year', 'to the months of the year', 'a night', 'a day and a night');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Identify a secondary planet', 2, 'pluto', 'neptune', 'jupiter', 'mercury');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The natural satellites of the solar system are stars that revolve around...', 2, 'of the main planets', 'of the secondary planets', 'of the stars', 'of the sun');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The Galilean moons orbit what planet?', 2, 'Jupiter', 'Mercury', 'Earth', 'Neptune');

-- Hard

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'In the morning, the sun is in a position...', 3, 'rising or east', 'rising or south', 'south', 'west or east');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'At midday sun is in position...', 3, 'south', 'rising or south', 'rising or east', 'west or east');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'At nightfall, the sun is in a position...', 3, 'west or east', 'rising or south', 'rising or east', 'south');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the best known representation to indicate the cardial points ?', 3, 'wind rose ', 'Rose of cardinals', 'Rose of the Cardinal Points', 'Orientation Rose');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Choose the most correct option. The stars...', 3, 'have their own light and emit light in all directions', 'have no light of their own and emit light in all directions', 'They have no light of their own and receive and reflect light coming from the planets', 'has its own light and receives and reflects light that comes from the stars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Choose the most correct option. The planets... ', 3, 'They have no light of their own and receive and reflect light that comes from the stars', 'have no light of their own and emit light in all directions', 'have no light of their own and receive and reflect light that comes from the planets', 'have their own light and emit light in all directions');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The "Big Bang" was a gigantic explosion that happened about 15 billion years ago and gave birth to...', 3, 'universe', 'sun', 'Earth', 'solar system');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The moon Titan orbits what planet?', 3, 'Saturn', 'Mercury', 'Mars', 'Jupiter');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What planet is nicknamed the "Red Planet"?', 3, 'Mars', 'Mercury', 'Venus', 'Jupiter');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The Galilean moons orbit what planet?', 3, 'Jupiter', 'Mercury', 'Earth', 'Neptune');

-- Insert SUBJECT and QUESTIONS -> HUMAN BODY

INSERT INTO `subjectseng` (`id`, `Name`, `Image`) VALUES (NULL, 'Human Body', NULL);
INSERT INTO `subjectspt` (`id`, `Name`, `Image`) VALUES (NULL, 'Corpo Humano', NULL); 

-- PT QUESTIONS

-- Easy

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Quantas camadas tem a nossa pele?', 1,'2','3','4','5');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Qual o maior orgão do corpo humano?', 1,'pele','fémur','intestino delgado', 'intestino grosso');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Como se chama os "buraquinhos" que temos na pele?', 1,'poros','ouvidos','pêlos', 'narinas');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Em caso de fratura o que devemos fazer?', 1,'imobilizar a zona fraturada','fazer movimentos de frição','pressionar zona fraturada', 'correr e saltar');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Produzem a saliva que é lançada no interior da boca:', 1,'glândulas salivares','boca','fígado', 'estômago');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','É composto pelos lábios, dentes e língua.', 1,'boca','pâncreas','faringe', 'glândulas salivares');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Tubo com cerca de 6 metros que liga o estômago ao intestino grosso.', 1,'intestino delgado','ânus','esófago', 'faringe');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Tubo com cerca de 1,5 metros mais largo que o intestino delgado, que se liga ao reto e ao ânus.', 1,'intestino grosso','pâncreas','esófago', 'figado');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Produz a bílis, um suco que fica armazenado na vesícula biliar.', 1,'fígado','pâncreas','estômago', 'intestino grosso');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Armazena a bílis que é lançada no intestino delgado.', 1,'vesícula bíliar','fígado','reto', 'faringe');

-- Medium

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','O que é o esqueleto?', 2,'um conjunto de ossos','um conjunto de orgãos','um conjunto de células', 'um conjunto de poros');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Como e que os ossos estão ligados entre si?', 2,'pelas articulações','pela coluna vertebral','pelas veias', 'pela omoplata');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Quais destes músculos são involuntários', 2,'músculos do coração','músculos da boca','músculos da perna', 'músculos do braço');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Quantos ossos tem o esqueleto humano', 2,'206','222','450', '999');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Tubo de cerca de 25cm que se estende da faringe ate ao estômago', 2,'esófago','vesícula biliar','reto', 'estômago');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Orifício por onde as fezes são expulsas para o exterior do corpo.', 2,'ânus','boca','intestino grosso', 'vesícula biliar');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Faz parte do sistema respiratório.', 2,'os pulmões','o estômago','o sangue', 'o coração');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Onde se transforma o bolo alimentar.', 2,'o estômago','o sangue','o coração', 'os pulmões');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Conseguimos mover-nos graças a...', 2,'ossos e músculos','músculos','pilhas', 'ossos');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','O cérebro é protegido pelo ...', 2,'crânio','coração','cabelo', 'chapéu');

-- Hard

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Como se chama o osso que temos na nossa testa?', 3,'frontal','temporal','parietal', 'ocipital');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Quantas vértebras te a coluna vertebral?', 3,'33','36','39', '30');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Que nome tem o osso do braço', 3,'úmero','rádio','carpo', 'cúbito');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Orgão que produz o suco gástrico que é lançado no indestino delgado', 3,'pâncreas','estômago','ânus', 'intestino grosso');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Parte final do intestino grosso onde são armazenadas as fezes antes de serem expulsas', 3,'reto','faringe','intestino grosso', 'estômago');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Orgão que armazena a bílis que é lançada no indestino delgado?', 3,'vesícula biliar','faringe','fígado', 'reto');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Canal que faz a ligação entre as fossas nasais e a laringe e entre a boca e o esófago', 3,'faringe','pâncreas','intestino delgado', 'estômago');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Orgão em forma de bolsa com paredes musculares e elásticas que contem o suco gástrico.', 3,'estômago','boca','intestino grosso', 'glândulas salivares');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Leva o alimento e o oxigénio a todas as células.', 3,'o sangue','o estômago','o coração', 'os pulmões');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2','Faz circular o sangue.', 3,'o coração','o estômago','o sangue', 'os pulmões');

-- ENG QUESTIONS

-- Easy

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'How many layers does our skin have?', 1, '2', '3', '4', '5');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'What is the largest organ in the human body?', 1, 'skin', 'femur', 'small intestine', 'large intestine');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'What do you call the "little holes" in our skin?', 1, 'pores', 'ears', 'hair', 'nostrils');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'In case of fracture what should we do?', 1, 'immobilize the fractured zone', 'make rubbing movements', 'press fractured zone', 'run and jump');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'They produce the saliva that is released inside the mouth.', 1, 'salivary glands', 'mouth', 'liver', 'stomach');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'It consists of the lips, teeth and tongue.', 1, 'mouth', 'pancreas', 'pharynx', 'salivary glands');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Tube about 6 meters long that connects the stomach to the large intestine.', 1, 'small intestine', 'anus', 'esophagus', 'pharynx');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'It produces bile, a juice that is stored in the gallbladder.', 1, 'liver', 'pancreas', 'stomach', 'large intestine');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'It stores the bile that is released into the small intestine.', 1, 'gallbladder', 'liver', 'straight', 'pharynx');

-- Medium

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'What is the skeleton?', 2, 'a group of bones', 'a group of organs', 'a group of cells', 'a group of pores');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'How are the bones connected together?', 2, 'through the joints', 'through the spine', 'through the veins', 'by the shoulder blade');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Which of these muscles are involuntary?', 2, 'heart muscles', 'mouth muscles', 'leg muscles', 'arm muscles');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'How many bones does the human skeleton have', 2, '206', '222', '450', '999');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Tube of about 25 cm that extends from the pharynx to the stomach', 2, 'esophagus', 'gallbladder', 'straight', 'stomach');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Hole through which feces are expelled from the body.', 2, 'anus', 'mouth', 'large intestine', 'gallbladder');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'It is part of the respiratory system.', 2, 'the lungs', 'the stomach', 'the blood', 'the heart');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Where the food bolus is transformed.', 2, 'the stomach', 'the blood', 'the heart', 'the lungs');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'We managed to move thanks to...', 2, 'bones and muscles', 'muscles', 'stacks', 'bones');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'The brain is protected by...', 2, 'skull', 'heart', 'hair', 'hat');

-- Hard

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'What is the name of the bone on our forehead?', 3, 'front', 'temporal', 'parietal', 'occipital');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'How many vertebrae does the spine have?', 3, '33', '36', '39', '30');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'What is the name of the arm bone', 3, 'humerus', 'radius bone', 'carpus', 'cubit');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Organ that produces gastric juice that is released into the small intestine', 3, 'pancreas', 'stomach', 'anus', 'large intestine');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Final part of the large intestine where feces are stored before being expelled', 3, 'straight', 'pharynx', 'large intestine', 'stomach');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Organ that stores bile that is released into the small intestine?', 3, 'gallbladder', 'pharynx', 'liver', 'straight');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Canal that connects the nasal passages to the larynx and between the mouth and esophagus', 3, 'pharynx', 'pancreas', 'small intestine', 'stomach');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'Bag-shaped organ with muscular and elastic walls that contain gastric juice?', 3, 'stomach', 'mouth', 'large intestine', 'salivary glands');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'It carries food and oxygen to all cells.', 3, 'the blood', 'the stomach', 'the heart', 'the lungs');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'It circulates the blood.', 3, 'the heart', 'the stomach', 'the blood', 'the lungs');
