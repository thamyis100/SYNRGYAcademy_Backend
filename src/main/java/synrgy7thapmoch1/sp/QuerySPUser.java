package synrgy7thapmoch1.sp;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class QuerySPUser {
    public String listUserPaginated = "CREATE OR REPLACE FUNCTION public.list_user_paginated(page_num int, page_size int)\n" +
            " RETURNS TABLE(\n" +
            "   user_id UUID,\n" +
            "   username VARCHAR,\n" +
            "   email_address VARCHAR\n" +
            ")\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "BEGIN\n" +
            "   RETURN QUERY\n" +
            "   SELECT id, username, email_address\n" +
            "   FROM users\n" +
            "   ORDER BY username\n" +
            "   LIMIT page_size OFFSET page_num * page_size;\n" +
            "END\n" +
            "$function$;\n";

    public String saveUser = "CREATE OR REPLACE PROCEDURE public.insert_employee(\n" +
            "    INOUT resid bigint,\n" +
            "    INOUT rqnama character varying,\n" +
            "    INOUT rqaddress character varying,\n" +
            "    INOUT rqdob date\n" +
            ")\n" +
            "LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "BEGIN\n" +
            "    IF rqnama IS NULL OR rqnama = '' THEN\n" +
            "        RAISE EXCEPTION 'Employee name is required';\n" +
            "    END IF;\n" +
            "    IF rqaddress IS NULL OR rqaddress = '' THEN\n" +
            "        RAISE EXCEPTION 'Employee address is required';\n" +
            "    END IF;\n" +
            "    IF rqdob IS NULL THEN\n" +
            "        RAISE EXCEPTION 'Employee date of birth is required';\n" +
            "    END IF;\n" +
            "    \n" +
            "    INSERT INTO public.employee\n" +
            "    (id, created_date, updated_date, address, dob, name, status)\n" +
            "    VALUES\n" +
            "    (nextval('employee_id_seq'), now(), now(), rqaddress, rqdob, rqnama, 'active')\n" +
            "    RETURNING id INTO resid;\n" +
            "END;\n" +
            "$procedure$;";

    public String updateUser = "CREATE OR REPLACE PROCEDURE public.update_user(\n" +
            "    inout resid bigint,\n" +
            "    inout rqusername character varying,\n" +
            "    inout rqemailaddress character varying,\n" +
            "    inout rqpassword character varying\n" +
            ")\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "begin\n" +
            "    if rqusername is null or rqusername = '' then \n" +
            "        raise exception 'Username is required';\n" +
            "    end if;\n" +
            "    if rqemailaddress is null or rqemailaddress = '' then \n" +
            "        raise exception 'Email Address is required';\n" +
            "    end if;\n" +
            "    if rqpassword is null or rqpassword = '' then \n" +
            "        raise exception 'Password is required';\n" +
            "    end if;\n" +
            "    update public.users\n" +
            "    set username = rqusername, email_address = rqemailaddress, password = rqpassword\n" +
            "    where id = resid;\n" +
            "end;\n" +
            "$procedure$;\n";

    public String deleteUser = "CREATE OR REPLACE PROCEDURE public.delete_user(inout resid bigint)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "begin\n" +
            "    delete from public.users\n" +
            "    where id = resid;\n" +
            "end;\n" +
            "$procedure$;\n";

    public String getUserById = "CREATE OR REPLACE FUNCTION public.get_user_by_id(\n" +
            "    rqid bigint\n" +
            ")\n" +
            " RETURNS TABLE(resid bigint, resusername character varying, resemailaddress character varying, respassword character varying)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "begin\n" +
            "    return query\n" +
            "    select id, username, email_address, password\n" +
            "    from public.users\n" +
            "    where id = rqid;\n" +
            "end;\n" +
            "$function$;\n";
}
