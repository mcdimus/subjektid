UPDATE subject_attribute_type SET type_name='business areas' WHERE subject_attribute_type=1;
UPDATE subject_attribute_type SET type_name='established' WHERE subject_attribute_type=2;
UPDATE subject_attribute_type SET type_name='Estonian resident' WHERE subject_attribute_type=3;
UPDATE subject_attribute_type SET type_name='number of children' WHERE subject_attribute_type=4;
UPDATE subject_attribute_type SET type_name='client interests' WHERE subject_attribute_type=5;
UPDATE subject_attribute_type SET type_name='discount %' WHERE subject_attribute_type=6;

UPDATE ent_per_relation_type SET type_name='company representative' WHERE ent_per_relation_type=1;
UPDATE ent_per_relation_type SET type_name='secretary' WHERE ent_per_relation_type=2;
UPDATE ent_per_relation_type SET type_name='sales representative' WHERE ent_per_relation_type=3;
UPDATE ent_per_relation_type SET type_name='board member' WHERE ent_per_relation_type=4;
UPDATE ent_per_relation_type SET type_name='head' WHERE ent_per_relation_type=5;