CREATE OR REPLACE FUNCTION func_delete_person(person_id integer)
  RETURNS text
AS $$
DECLARE
  answer text;
  subject_type integer default 1;
  employee_id integer default 0;
  customer_id integer default 0;
BEGIN
  IF person_id IN (SELECT person_fk FROM enterprise_person_relation) THEN
    RAISE NOTICE 'will not delete anything';
    answer := 'NOT DELETED';
  ELSE
    SELECT INTO customer_id customer.customer FROM customer WHERE customer.subject_type_fk=subject_type AND customer.subject_fk=person_id LIMIT 1;
    SELECT INTO employee_id employee.employee FROM employee WHERE employee.person_fk=person_id LIMIT 1;
    
    DELETE FROM address WHERE address.subject_type_fk=subject_type AND address.subject_fk=person_id;
    DELETE FROM contact WHERE contact.subject_type_fk=subject_type AND contact.subject_fk=person_id;
    DELETE FROM customer WHERE customer.subject_type_fk=subject_type AND customer.subject_fk=person_id;
    DELETE FROM person WHERE person.person=person_id;
    DELETE FROM employee_role WHERE employee_role.employee_fk=employee_id;    
    DELETE FROM user_account WHERE user_account.subject_type_fk=3 AND user_account.subject_fk=employee_id;
    DELETE FROM subject_attribute WHERE subject_attribute.subject_type_fk IN (1,3,4) AND (subject_attribute.subject_fk=person_id OR subject_attribute.subject_fk=employee_id OR subject_attribute.subject_fk=customer_id);
    
    answer := 'OK';
  END IF;

  RETURN answer;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION func_delete_enterprise(enterprise_id integer)
  RETURNS text
AS $$
DECLARE
  answer text;
  subject_type integer default 2;
  customer_id integer default 0;
BEGIN
    SELECT INTO customer_id customer.customer FROM customer WHERE customer.subject_type_fk=subject_type AND customer.subject_fk=enterprise_id LIMIT 1;

    DELETE FROM address WHERE address.subject_type_fk=subject_type AND address.subject_fk=enterprise_id;
    DELETE FROM contact WHERE contact.subject_type_fk=subject_type AND contact.subject_fk=enterprise_id;
    DELETE FROM customer WHERE customer.subject_type_fk=subject_type AND customer.subject_fk=enterprise_id;
    DELETE FROM enterprise WHERE enterprise.enterprise=enterprise_id;
    DELETE FROM enterprise_person_relation WHERE enterprise_person_relation.enterprise_fk=enterprise_id;
    DELETE FROM subject_attribute WHERE subject_attribute.subject_type_fk IN (2) AND (subject_attribute.subject_fk=enterprise_id OR subject_attribute.subject_fk=customer_id);
    
    answer := 'OK';
  RETURN answer;
END;
$$ LANGUAGE plpgsql;
