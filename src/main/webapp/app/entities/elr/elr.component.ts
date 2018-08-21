import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IELR } from 'app/shared/model/elr.model';
import { Principal } from 'app/core';
import { ELRService } from './elr.service';

@Component({
    selector: 'jhi-elr',
    templateUrl: './elr.component.html'
})
export class ELRComponent implements OnInit, OnDestroy {
    eLRS: IELR[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private eLRService: ELRService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.eLRService.query().subscribe(
            (res: HttpResponse<IELR[]>) => {
                this.eLRS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInELRS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IELR) {
        return item.id;
    }

    registerChangeInELRS() {
        this.eventSubscriber = this.eventManager.subscribe('eLRListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
