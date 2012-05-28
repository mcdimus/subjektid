/* Person attributes */
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (1, 'gender', 1, 3, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (1, 'nationality', 1, 4, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (1, 'religion', 1, 5, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (1, 'driving license', 1, 6, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (1, 'fauvorite number', 2, 7, 'N', 'N');

/* Enterprise attributes */
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (2, 'industry', 1, 3, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (2, 'national', 1, 4, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (2, 'enteprise type', 1, 5, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (2, 'employees amount', 2, 6, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (2, 'year revenue', 2, 7, 'N', 'N');

/* Employee attributes */
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (3, 'employee since', 3, 1, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (3, 'iq', 2, 2, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (3, 'salary', 2, 3, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (3, 'insured', 1, 4, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (3, 'first aid course', 1, 5, 'N', 'N');

/* Client attributes */
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (4, 'client since', 3, 3, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (4, 'client evel', 1, 4, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (4, 'vip client', 1, 5, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (4, 'last purchase', 2, 6, 'N', 'N');
INSERT INTO subject_attribute_type (subject_type_fk, type_name, data_type, orderby, required, multiple_attributes) VALUES (4, 'debt', 2, 7, 'N', 'Y');
