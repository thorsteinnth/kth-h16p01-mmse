package com.thorsteinnth.kth.mmse.sepcli.Service;

import com.thorsteinnth.kth.mmse.sepcli.Domain.AccessFunction;
import com.thorsteinnth.kth.mmse.sepcli.Domain.User;

import java.util.ArrayList;
import java.util.Arrays;

public class AccessControlService
{
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
                        AccessFunction.approveEventRequest,
                        AccessFunction.rejectEventRequest,
                        AccessFunction.browseEventRequests
                ));
                break;
            case FinancialManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.editEventRequest,
                        AccessFunction.approveFinancialRequest,
                        AccessFunction.rejectFinancialRequest,
                        AccessFunction.browseClientRecords
                ));
                break;
            case ProductionManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.editEventRequest,
                        AccessFunction.createFinancialRequest,
                        AccessFunction.createTaskRequest,
                        AccessFunction.createRecruitmentRequest
                ));
                break;
            case ServiceDepartmentManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.editEventRequest,
                        AccessFunction.createFinancialRequest,
                        AccessFunction.createTaskRequest,
                        AccessFunction.createRecruitmentRequest
                ));
                break;
            case ProductionDepartmentSubTeamEmployee:
                accessList.add(AccessFunction.editEventRequest);
                break;
            case ServiceDepartmentSubTeamEmployee:
                accessList.add(AccessFunction.editEventRequest);
                break;
            case MarketingAssistant:
                accessList.add(AccessFunction.browseClientRecords);
                break;
            case MarketingOfficer:
                accessList.add(AccessFunction.browseClientRecords);
                break;
            case CustomerServiceOfficer:
                accessList.add(AccessFunction.createEventRequest);
                break;
            case SeniorCustomerServiceOfficer:
                accessList.addAll(Arrays.asList(
                        AccessFunction.rejectEventRequest,
                        AccessFunction.createClientRecord,
                        AccessFunction.browseClientRecords,
                        AccessFunction.browseEventRequests
                ));
                break;
            case SeniorHRManager:
                accessList.addAll(Arrays.asList(
                        AccessFunction.approveRecruitmentRequest,
                        AccessFunction.rejectRecruitmentRequest
                ));
                break;
        }

        return accessList;
    }
}