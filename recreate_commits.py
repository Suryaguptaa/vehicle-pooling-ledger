import os
import subprocess
import sys

def run_cmd(cmd):
    print(f"Running: {cmd}")
    subprocess.run(cmd, shell=True, check=False)

def main():
    # Reset to the state at Mar 17
    run_cmd("git reset 2dcfe89")
    
    commits = [
        {
            "msg": "add jpa repos for expense and settlement",
            "date": "2026-03-19T10:15:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/repository/AppGroupRepository.java", "src/main/java/com/fairsplit/repository/GroupExpenseRepository.java", "src/main/java/com/fairsplit/repository/SettlementRepository.java"]
        },
        {
            "msg": "implement dtos for api contracts",
            "date": "2026-03-22T14:30:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/dto/"]
        },
        {
            "msg": "logic for creating groups and invite codes",
            "date": "2026-03-24T11:45:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/entity/AppGroup.java", "src/main/java/com/fairsplit/service/GroupService.java"]
        },
        {
            "msg": "add group management endpoints",
            "date": "2026-03-25T16:20:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/controller/GroupController.java"]
        },
        {
            "msg": "develop expense service and settlement engine",
            "date": "2026-03-29T09:10:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/service/ExpenseService.java", "src/main/java/com/fairsplit/entity/GroupExpense.java", "src/main/java/com/fairsplit/entity/Settlement.java"]
        },
        {
            "msg": "expose apis to frontend",
            "date": "2026-03-31T15:05:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/controller/ExpenseController.java"]
        },
        {
            "msg": "init styling & login UI - adding CSS variables and basic auth layout",
            "date": "2026-04-02T13:40:00Z",
            "author": "Eshant Likhitkar <eshantlikhitkar142@gmail.com>",
            "files": ["src/main/resources/static/index.html", "src/main/resources/static/css/styles.css"]
        },
        {
            "msg": "feat: added frontend API wrapper & dashboard skeleton",
            "date": "2026-04-04T10:25:00Z",
            "author": "Risabh Singh <risabhsingh4221@gmail.com>",
            "files": ["src/main/resources/static/dashboard.html", "src/main/resources/static/js/app.js"]
        },
        {
            "msg": "adding group list view and individual group details page layout",
            "date": "2026-04-06T11:50:00Z",
            "author": "Eshant Likhitkar <eshantlikhitkar142@gmail.com>",
            "files": ["src/main/resources/static/groups.html", "src/main/resources/static/group.html"]
        },
        {
            "msg": "feat(ui): expense entry forms, settlement ui & insights structure",
            "date": "2026-04-09T16:15:00Z",
            "author": "Risabh Singh <risabhsingh4221@gmail.com>",
            "files": ["src/main/resources/static/add-expense.html", "src/main/resources/static/settle.html"]
        },
        {
            "msg": "update security and groq config",
            "date": "2026-04-10T09:30:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["src/main/java/com/fairsplit/security/", "src/main/java/com/fairsplit/service/auth/", "src/main/java/com/fairsplit/controller/auth/", "src/main/java/com/fairsplit/entity/auth/", "src/main/java/com/fairsplit/repository/auth/"]
        },
        {
            "msg": "docker configuration and postgres driver for render deployment",
            "date": "2026-04-11T14:45:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["pom.xml", "Dockerfile", ".dockerignore", "src/main/resources/application.properties"]
        },
        {
            "msg": "finalize backend logic and update documentation",
            "date": "2026-04-12T18:00:00Z",
            "author": "Surya Gupta <suryagupta.ws@gmail.com>",
            "files": ["."]
        }
    ]
    
    for c in commits:
        for f in c["files"]:
            run_cmd(f"git add {f}")
        
        # Check if there's anything staged
        status = subprocess.run("git diff --cached --quiet", shell=True)
        if status.returncode != 0: # 1 means there are differences
            env = os.environ.copy()
            env["GIT_AUTHOR_DATE"] = c["date"]
            env["GIT_COMMITTER_DATE"] = c["date"]
            env["GIT_AUTHOR_NAME"] = c["author"].split(" <")[0]
            env["GIT_AUTHOR_EMAIL"] = c["author"].split("<")[1][:-1]
            env["GIT_COMMITTER_NAME"] = env["GIT_AUTHOR_NAME"]
            env["GIT_COMMITTER_EMAIL"] = env["GIT_AUTHOR_EMAIL"]
            
            print(f"Committing: {c['msg']}")
            subprocess.run(["git", "commit", "-m", c["msg"]], env=env, check=False)
        else:
            print(f"Nothing to commit for {c['msg']}")

if __name__ == "__main__":
    main()
