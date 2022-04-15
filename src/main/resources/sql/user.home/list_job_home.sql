SELECT * FROM Jobs INNER  JOIN Status_Job on Status_Job.id = Jobs.status_job_id
WHERE 1=1 AND Status_Job.status_name IN('APPROVED' ,'RECRUITING')  AND Jobs.is_delete = 0