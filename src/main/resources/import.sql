INSERT INTO `employeprofile` (`id`, `type`) VALUES ('1', 'ADMIN'),('2', 'USER');
INSERT INTO `employe` (`idEmploye`, `civilite`, `classe`, `derictor`, `email`, `fonction`, `matricule`, `nom`, `password`, `prenom`, `rib`, `id_dept`, `id_grade`, `vehicule_id`,`avoir`) VALUES ('100', 'M', NULL, b'0', NULL, NULL, '3040', 'Assistante', '3040', '', 'DAF', 4, 5, NULL,0);
INSERT INTO `app_user_user_profile` (`USER_ID`, `USER_PROFILE_ID`) VALUES ('100', '2');
INSERT INTO `app_user_user_profile` (`USER_ID`, `USER_PROFILE_ID`) VALUES ('100', '1');
INSERT INTO `dept` (`id`, `nom`, `chef_idEmploye`) VALUES ('1', 'DSAE', NULL), ('2', 'DLEC', NULL), ('3', 'DG', NULL), ('4', 'DAF', NULL), ('5', 'DR&D', NULL), ('6', 'DE', NULL), ('7', 'SI', NULL), ('8', 'SRH', NULL), ('9', 'CFA', NULL), ('10', 'CDC', NULL);
INSERT INTO `grade` (`id`, `label`, `type`) VALUES (1, 'Derecteur Generale', 'DG    '),(2, 'Directeur général adjoint', 'DGA'), (3, 'Chef de Service', 'CHEF'),(4, 'Chef de service adjoint(e)', 'CHEFA'),(5, 'Assistant(e)', 'ASSISTANT'),(6, 'AUTRE', 'AUTRE');
