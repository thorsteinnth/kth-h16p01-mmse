package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.AppData;
import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;
import java.util.Arrays;

public class AccessControlService
{
    public static boolean hasAccess(AccessFunction accessFunction)
    {
        return hasAccess(AppData.loggedInUser, accessFunction);
    }

    public static boolean hasAccess(User user, AccessFunction accessFunction)
    {
        ArrayList<AccessFunction> accessListForUser = getAccessListForRole(user.role);

        boolean hasAccess = accessListForUser.contains(accessFunction);

        return hasAccess;
    }

    private static ArrayList<AccessFunction> getAccessListForRole(User.Role role)
    {
        ArrayList<AccessFunction> accessList = new ArrayList<>();

        switch (role)
        {
            case AdministrationDepartmentManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.requestManagement,

                        AccessFunction.eventRequestManagement,
                        AccessFunction.approveEventRequest,
                        AccessFunction.rejectEventRequest,
                        AccessFunction.browseEventRequests
                ));
                break;

            case FinancialManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.requestManagement,

                        AccessFunction.eventRequestManagement,
                        AccessFunction.editEventRequest,

                        AccessFunction.financialRequestManagement,
                        AccessFunction.approveFinancialRequest,
                        AccessFunction.rejectFinancialRequest,

                        AccessFunction.clientManagement,
                        AccessFunction.browseClientRecords
                ));
                break;

            case ProductionManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.requestManagement,

                        AccessFunction.eventRequestManagement,
                        AccessFunction.editEventRequest,

                        AccessFunction.financialRequestManagement,
                        AccessFunction.createFinancialRequest,

                        AccessFunction.taskRequestManagement,
                        AccessFunction.createTaskRequest,

                        AccessFunction.recruitmentRequestManagement,
                        AccessFunction.createRecruitmentRequest
                ));
                break;

            case ServiceDepartmentManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.requestManagement,

                        AccessFunction.eventRequestManagement,
                        AccessFunction.editEventRequest,

                        AccessFunction.financialRequestManagement,
                        AccessFunction.createFinancialRequest,

                        AccessFunction.taskRequestManagement,
                        AccessFunction.createTaskRequest,

                        AccessFunction.recruitmentRequestManagement,
                        AccessFunction.createRecruitmentRequest
                ));
                break;

            case ProductionDepartmentSubTeamEmployee:
                accessList.add(AccessFunction.requestManagement);

                accessList.add(AccessFunction.taskRequestManagement);
                accessList.add(AccessFunction.editTaskRequest);
                break;

            case ServiceDepartmentSubTeamEmployee:
                accessList.add(AccessFunction.requestManagement);

                accessList.add(AccessFunction.taskRequestManagement);
                accessList.add(AccessFunction.editTaskRequest);
                break;

            case MarketingAssistant:
                accessList.add(AccessFunction.clientManagement);
                accessList.add(AccessFunction.browseClientRecords);
                break;

            case MarketingOfficer:
                accessList.add(AccessFunction.clientManagement);
                accessList.add(AccessFunction.browseClientRecords);
                break;

            case CustomerServiceOfficer:
                accessList.add(AccessFunction.requestManagement);

                accessList.add(AccessFunction.eventRequestManagement);
                accessList.add(AccessFunction.createEventRequest);
                break;

            case SeniorCustomerServiceOfficer:
                accessList.addAll(Arrays.asList(
                        AccessFunction.requestManagement,

                        AccessFunction.eventRequestManagement,
                        AccessFunction.rejectEventRequest,
                        AccessFunction.browseEventRequests,

                        AccessFunction.clientManagement,
                        AccessFunction.createClientRecord,
                        AccessFunction.browseClientRecords
                ));
                break;

            case SeniorHRManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.requestManagement,

                        AccessFunction.recruitmentRequestManagement,
                        AccessFunction.approveRecruitmentRequest,
                        AccessFunction.rejectRecruitmentRequest
                ));
                break;
        }

        return accessList;
    }
}