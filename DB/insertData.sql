-- Insert SUBJECT and QUESTIONS -> PLANETS

INSERT INTO `subjects` (`id`, `Name`, `Image`) VALUES (NULL, 'Planets', NULL); 

INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais pequeno do Sistema Solar?', 1, 'Mercúrio', 'Júpiter', 'Vénus', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o maior planeta do Sistema Solar?', 1, 'Júpiter', 'Saturno', 'Neptuno', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais quente do Sistema Solar?', 1, 'Vénus', 'Mercúrio', 'Terra', 'Júpiter');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'O sexto planeta do Sistema Solar possui um extenso anel, qual é o nome desse planeta?', 1, 'Saturno', 'Júpiter', 'Neptuno', 'Marte');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'O elemento químico Urânio foi nomeado depois de que planeta?', 2, 'Urânio', 'Saturno', 'Marte', 'Terra');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais distante do sol, no Sistema Solar', 2, 'Neptuno', 'Urânio', 'Marte', 'Mercúrio');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o segundo planeta mais pequeno, dentro do Sistema Solar?', 2, 'Marte', 'Mercúrio', 'Terra', 'Neptuno');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta mais próximo em tamanho da Terra?', 2, 'Vénus', 'Mercúrio', 'Marte', 'Saturno');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'A lua Titã orbita que planeta?', 3, 'Saturno', 'Mercúrio', 'Marte', 'Júpiter');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o planeta denomidado por "Planeta Vermelho?"', 3, 'Marte', 'Mercúrio', 'Vénus', 'Júpiter');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'As luas da Galiléia orbitam que planeta?', 3, 'Júpiter', 'Mercúrio', 'Terra', 'Neptuno');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'A lua Tritão é a maior lua de que planeta?', 3, 'Neptuno', 'Mercúrio', 'Júpiter', 'Marte');

INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the smallest planet in the Solar System?', 1, 'Mercury', 'Jupiter', 'Venus', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the largest planet in the Solar System?', 1, 'Jupiter', 'Saturn', 'Neptune', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the hottest planet in the Solar System?', 1, 'Venus', 'Mercury', 'Earth', 'Jupiter');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The sixth planet from the Sun features an extensive ring system, what is the name of this planet?', 1, 'Saturn', 'Jupiter', 'Neptune', 'Mars');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The chemical element uranium was named after what planet?', 2, 'Uranus', 'Saturn', 'Mars', 'Earth');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What planet in the solar system is farthest from the Sun?', 2, 'Neptune', 'Uranus', 'Mars', 'Mercury');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What is the second smallest planet in the solar system?', 2, 'Mars', 'Mercury', 'Earth', 'Neptune');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What planet is closest in size to Earth?', 2, 'Venus', 'Mercury', 'Mars', 'Saturn');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The moon Titan orbits what planet?', 3, 'Saturn', 'Mercury', 'Mars', 'Jupiter');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'What planet is nicknamed the "Red Planet"?', 3, 'Mars', 'Mercury', 'Venus', 'Jupiter');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'The Galilean moons orbit what planet?', 3, 'Jupiter', 'Mercury', 'Earth', 'Neptune');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Triton is the largest moon of what planet?', 3, 'Neptune', 'Mercury', 'Jupiter', 'Mars');

-- Insert SUBJECT and QUESTIONS -> HUMAN BODY

INSERT INTO `subjects` (`id`, `Name`, `Image`) VALUES (NULL, 'Human Body', NULL);
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o maior planeta do sistema solar?', 'facil', 'Jupiter', 'Marte', 'Terra', 'Saturno');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'Qual é o animal marinho?', 'dificil', 'peixe', 'cavalo', 'passaro', 'girafa');
INSERT INTO `questions_pt` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'qual é a resposta certa de portugues?', 'facil', 'esta é a certa', 'esta está errada1', 'esta está errada2', 'esta está errada3');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '1', 'what is the biggest planet on the solar system?', 'easy', 'Jupiter', 'Mars', 'Earth', 'Saturn');
INSERT INTO `questions_eng` (`id`, `IDSubject`, `Question`, `Difficulty`, `RightAnswer`, `Wrong1`, `Wrong2`, `Wrong3`) VALUES (NULL, '2', 'what is a portuguese word?', 'hard', 'ola', 'hello', 'good', 'hola');


-- Insert Results 
INSERT INTO `userresults` (`id`, `IDSubject`, `IDUser`, `Score`, `NumCorrectAns`, `NumWrongAns`, `TimeToSolve`, `Difficulty`) VALUES (NULL, '2', 1, 90, 9, 1, '09-12-2022 12:18:34', 2);
INSERT INTO `userresults` (`id`, `IDSubject`, `IDUser`, `Score`, `NumCorrectAns`, `NumWrongAns`, `TimeToSolve`, `Difficulty`) VALUES (NULL, '2', 3, 75, 6, 4, '09-12-2022 17:18:34', 2);